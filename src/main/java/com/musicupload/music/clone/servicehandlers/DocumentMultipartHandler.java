package com.musicupload.music.clone.servicehandlers;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.musicupload.music.clone.entity.Documents;
import com.musicupload.music.clone.entity.Musics;
import com.musicupload.music.clone.exceptions.DuplicateFilePath;
import com.musicupload.music.clone.exceptions.FileExtensionEmpty;
import com.musicupload.music.clone.exceptions.FileExtensionNotSupported;
import com.musicupload.music.clone.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class DocumentMultipartHandler {

    private final AmazonS3 amazonS3;
    private final DocumentRepository documentRepository;

    @Value("${music-upload-s3-bucket}")
    private String s3Bucket;

    public Musics saveDocuments(
            MultipartFile[] files,
            Long userId,
            String musicName) throws IOException, NoSuchAlgorithmException {

        if (files == null || files.length != 1)
            throw new IllegalArgumentException("Invalid files provided");

        String musicFileName = (musicName == null || musicName.isEmpty())
                ? files[0].getName()
                : musicName;


        // Get the file from the list of the Files
        MultipartFile multipartFile = files[0];

        // Convert the multipart to the file object
        File file = convertMultiPartToFile(multipartFile);
        // Create hash key from the file object
        String hashKey = createHashKey(file);
        if(documentRepository.existsByHashKey(hashKey))
            throw new DuplicateFilePath("File already exists");

        Documents documents = getTheDocumentsForDatabase(multipartFile, hashKey);
        amazonS3.putObject(
                new PutObjectRequest(s3Bucket, documents.getName(), file)
                        .withCannedAcl(CannedAccessControlList.PublicRead)
        );
        if(file.exists()){
            boolean isDeleteSuccess = file.delete();
            if(isDeleteSuccess) log.info("File deleted successfully");
        }

        return Musics.builder()
                .name(musicFileName)
                .userId(userId)
                .documents(documents)
                .build();
    }

    // Handle this with headers in the frontend with each partition to end the packets.
    // Window function to handle this will work and everything will be handle in the frontend to handle the music play
    public ResponseEntity<InputStreamResource> getFileStreamFromFile(Musics musics, String randHeader){
        try{
            String s3FilePath = musics.getDocuments().getName();
            var objectMetadata = amazonS3.getObjectMetadata(s3Bucket, s3FilePath);
            long fileSize = objectMetadata.getContentLength();
            String contentType = objectMetadata.getContentType();
            if(contentType == null) contentType = "audio/mpeg";

            if(randHeader == null){
                S3Object s3Object = amazonS3.getObject(s3Bucket, s3FilePath);
                S3ObjectInputStream inputStream = s3Object.getObjectContent();

                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(fileSize))
                        .header(HttpHeaders.ACCEPT_RANGES, "bytes")
                        .body(new InputStreamResource(inputStream));
            }

            String[] ranges = randHeader.replace("bytes=", "").split("-");
            long rangeStart = Long.parseLong(ranges[0]);
            long rangeEnd = ranges.length > 1 && !ranges[1].isEmpty() ? Long.parseLong(ranges[1]) : fileSize-1;

            long contentLength = rangeEnd - rangeStart + 1;
            GetObjectRequest getObjectRequest = new GetObjectRequest(s3Bucket, s3FilePath)
                    .withRange(rangeStart, rangeEnd);

            S3Object s3Object = amazonS3.getObject(getObjectRequest);
            S3ObjectInputStream inputStream = s3Object.getObjectContent();

            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(contentLength))
                    .header(HttpHeaders.ACCEPT_RANGES, "bytes")
                    .header(HttpHeaders.CONTENT_RANGE, String.format("bytes %d-%d/%d", rangeStart, rangeEnd, fileSize))
                    .body(new InputStreamResource(inputStream));

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private Documents getTheDocumentsForDatabase(MultipartFile file, String hashKey) {
        String fileExtension = null;
        String originalFilename = file.getOriginalFilename();
        String uniqueFileName = UUID.randomUUID().toString();

        if (originalFilename != null) {
            int dotIndex = originalFilename.lastIndexOf('.');
            if (dotIndex > 0 && dotIndex < originalFilename.length() - 1) {
                fileExtension = originalFilename.substring(dotIndex + 1).toLowerCase();
            }
        }

        if (fileExtension == null || fileExtension.isEmpty())
            throw new FileExtensionEmpty("File extension is empty");

        if (!fileExtension.equals("mp3"))
            throw new FileExtensionNotSupported("File extension is not supported " + fileExtension);

        return Documents.builder()
                .name(uniqueFileName + "-" + originalFilename)
                .fileExtension(fileExtension)
                .size(file.getSize())
                .hashKey(hashKey)
                .build();
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    private static String createHashKey(File file) throws NoSuchAlgorithmException, IOException {
        // Build Hashing function and the hashkey from the file bytes
        StringBuilder hashBuilder = new StringBuilder();
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] fileBytes = messageDigest.digest(Files.readAllBytes(file.toPath()));
        for (byte b : fileBytes) {
            hashBuilder.append(String.format("%02x", b));
        }
        return hashBuilder.toString();
    }
}

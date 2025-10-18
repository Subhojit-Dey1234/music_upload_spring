package com.musicupload.music.clone.servicehandlers;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.musicupload.music.clone.entity.Documents;
import com.musicupload.music.clone.entity.Musics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${music-upload-s3-bucket}")
    private String s3Bucket;

    public Musics saveDocuments(
            MultipartFile[] files,
            Long userId,
            String musicName) throws IOException, NoSuchAlgorithmException {

        if (files == null || files.length != 1)
            throw new IllegalArgumentException("Invalid files provided");

        String musicFileName = (musicName == null || !musicName.isEmpty())
                ? files[0].getName()
                : musicName;

        MultipartFile multipartFile = files[0];
        File file = convertMultiPartToFile(multipartFile);

        // Build Hashing function and the hashkey from the file bytes
        StringBuilder hashBuilder = new StringBuilder();
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] fileBytes = messageDigest.digest(Files.readAllBytes(file.toPath()));
        for(byte b : fileBytes) {
            hashBuilder.append(String.format("%02x", b));
        }

        // Documents builder from the file
        Documents documents = getTheDocumentsForDatabase(multipartFile, hashBuilder.toString());
        amazonS3.putObject(
                new PutObjectRequest(s3Bucket, documents.getName(), file)
                .withCannedAcl(CannedAccessControlList.PublicRead)
        );


        return Musics.builder()
                .name(musicFileName)
                .userId(userId)
                .documents(documents)
                .build();
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
}

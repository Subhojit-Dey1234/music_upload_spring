package com.musicupload.music.clone.servicehandlers;

import com.musicupload.music.clone.entity.Documents;
import com.musicupload.music.clone.entity.Musics;
import com.musicupload.music.clone.repository.MusicRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class DocumentMultipartHandler {

    private final static String RESOURCES_LOCATION = "src/main/resources/";
    private final MusicRepository musicRepository;

    @Transactional
    public void saveDocuments(MultipartFile[] files) throws IOException {
        if(files == null || files.length != 1)
            throw new IllegalArgumentException("Invalid files provided");

        Documents documents = saveDocumentLocally(files[0]);
        Musics musics = Musics.builder()
                .name("Testing with music name")
                .userId(1L)
                .documents(documents)
                .build();
        Musics musics1 = musicRepository.save(musics);
        System.out.println(musics1.getId());
    }

    private static Documents saveDocumentLocally(MultipartFile file) throws IOException {
        Files.copy(
                file.getInputStream(), Path.of(RESOURCES_LOCATION + file.getOriginalFilename())
        );

        return Documents.builder()
                .name(UUID.randomUUID().toString())
                .musicId(1L)
                .build();
    }
}

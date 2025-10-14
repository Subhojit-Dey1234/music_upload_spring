package com.musicupload.music.clone.servicehandlers;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@Slf4j
@RequiredArgsConstructor
public class DocumentMultipartHandler {

    private final static String RESOURCES_LOCATION = "src/main/resources/";

    @Transactional
    public void saveDocuments(MultipartFile[] files) throws IOException {
        for (MultipartFile file : files) saveDocumentLocally(file);
    }

    private static void saveDocumentLocally(MultipartFile file) throws IOException {
        Files.copy(
                file.getInputStream(), Path.of(RESOURCES_LOCATION + file.getOriginalFilename())
        );
    }
}

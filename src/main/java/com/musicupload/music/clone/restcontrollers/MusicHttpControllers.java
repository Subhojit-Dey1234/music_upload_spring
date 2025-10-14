package com.musicupload.music.clone.restcontrollers;


import com.musicupload.music.clone.entity.Musics;
import com.musicupload.music.clone.repository.MusicRepository;
import com.musicupload.music.clone.servicehandlers.DocumentMultipartHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/musics")
@RequiredArgsConstructor
public class MusicHttpControllers {

    private final MusicRepository musicRepository;
    private final DocumentMultipartHandler documentMultipartHandler;

    @GetMapping(path = {"/all", "/all/"})
    public List<Musics> getAllMusics() {
        return musicRepository.findAll();
    }

    @GetMapping(path = {"/by-user/{userId}", "/by-user/{userId}/"})
    public List<Musics> getAllMusicsByUserId(@PathVariable Long userId) {
        return musicRepository.findAllByUserId(userId);
    }

    @PostMapping(value = "/", consumes = "multipart/form-data")
    public void saveMusic(@RequestParam(value = "documents") MultipartFile[] documents) throws IOException {
        documentMultipartHandler.saveDocuments(documents);
    }
}

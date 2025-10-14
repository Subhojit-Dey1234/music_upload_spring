package com.musicupload.music.clone.controllers;


import com.musicupload.music.clone.entity.Musics;
import com.musicupload.music.clone.repository.MusicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/musics")
@RequiredArgsConstructor
public class MusicHttpControllers {

    private final MusicRepository musicRepository;

    @GetMapping(path = {"/all", "/all/"})
    public List<Musics> getAllMusics() {
        return musicRepository.findAll();
    }

    @GetMapping(path = {"/by-user/{userId}", "/by-user/{userId}/"})
    public List<Musics> getAllMusicsByUserId(@PathVariable Long userId) {
        return musicRepository.findAllByUserId(userId);
    }
}

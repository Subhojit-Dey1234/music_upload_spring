package com.musicupload.music.clone.repository;

import com.musicupload.music.clone.entity.Musics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MusicRepository extends JpaRepository<Musics, Long> {
    public List<Musics> findAllByUserId(Long userId);
}

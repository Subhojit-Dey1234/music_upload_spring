package com.musicupload.music.clone.repository;

import com.musicupload.music.clone.entity.Documents;
import com.musicupload.music.clone.entity.Musics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Documents, Long> {
    public boolean existsByHashKey(String hashKey);
}
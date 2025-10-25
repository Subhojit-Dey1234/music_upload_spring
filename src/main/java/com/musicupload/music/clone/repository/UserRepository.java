package com.musicupload.music.clone.repository;

import com.musicupload.music.clone.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {}

package com.musicupload.music.clone.repository;

import com.musicupload.music.clone.entity.UserComments;
import com.musicupload.music.clone.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCommentsRepository extends JpaRepository<UserComments, Long> {}

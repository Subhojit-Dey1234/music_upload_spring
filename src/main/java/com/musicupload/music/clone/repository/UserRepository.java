package com.musicupload.music.clone.repository;

import com.musicupload.music.clone.dto.UserBaseProjection;
import com.musicupload.music.clone.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<Users, Long> {

    @Query(value = """
            SELECT u.id as id, u.name as name, u.email as email, u.createdAt as createdAt FROM Users u
            """)
    List<UserBaseProjection> findAllUsers();
}
package com.musicupload.music.clone.restcontrollers;

import com.musicupload.music.clone.dto.UserPost;
import com.musicupload.music.clone.entity.Users;
import com.musicupload.music.clone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserHttpControllers {

    private final UserRepository userRepository;

    @GetMapping
    public List<Users> findAll() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public UserPost findById(@PathVariable Long id) {
        Users users = userRepository.findById(id)
                .orElseThrow();
        return UserPost.builder()
                .id(users.getId())
                .name(users.getName())
                .email(users.getEmail())
                .localDateTime(users.getCreatedAt())
                .musicsList(users.getMusicLiked())
                .comments(users.getCommentedPosts())
                .build();
    }
}

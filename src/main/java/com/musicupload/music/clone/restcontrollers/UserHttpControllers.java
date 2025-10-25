package com.musicupload.music.clone.restcontrollers;

import com.musicupload.music.clone.entity.Users;
import com.musicupload.music.clone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Users findById(@PathVariable Long id) {
        return userRepository.findById(id)
                .orElseThrow();
    }
}

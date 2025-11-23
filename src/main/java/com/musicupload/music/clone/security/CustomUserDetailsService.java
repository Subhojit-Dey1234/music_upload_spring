package com.musicupload.music.clone.security;

import com.musicupload.music.clone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.musicupload.music.clone.entity.Users;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users users = userRepository.findByEmail(email).orElseThrow(
                ()-> new UsernameNotFoundException("User not found with email: " + email)
        );

        return User.builder()
                .username(users.getEmail())
                .password(users.getPassword())
                .authorities(
                        Collections.singleton(
                                new SimpleGrantedAuthority("ROLE_USER")
                        )
                )
                .build();
    }
}

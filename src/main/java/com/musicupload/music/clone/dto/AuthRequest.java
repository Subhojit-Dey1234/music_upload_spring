package com.musicupload.music.clone.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {
    private String email;
    private String password;
}

// AuthResponse.java
@Data
@NoArgsConstructor
@AllArgsConstructor
class AuthResponse {
    private String token;
    private Long userId;
    private String name;
    private String email;
}

// RegisterRequest.java
@Data
@NoArgsConstructor
@AllArgsConstructor
class RegisterRequest {
    private String name;
    private String email;
    private String password;
}
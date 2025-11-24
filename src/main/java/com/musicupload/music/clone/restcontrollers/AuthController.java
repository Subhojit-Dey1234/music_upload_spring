package com.musicupload.music.clone.restcontrollers;

import com.musicupload.music.clone.dto.AuthRequest;
import com.musicupload.music.clone.dto.AuthResponse;
import com.musicupload.music.clone.dto.RegisterRequest;
import com.musicupload.music.clone.entity.Users;
import com.musicupload.music.clone.repository.UserRepository;
import com.musicupload.music.clone.security.CustomUserDetailsService;
import com.musicupload.music.clone.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getEmail(),
                            authRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid email or password");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());
        final Users user = userDetailsService.loadUserEntityByEmail(authRequest.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername(), user.getId());

        return ResponseEntity.ok(new AuthResponse(jwt, user.getId(), user.getName(), user.getEmail()));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Email already registered");
        }

        // Create new user
        Users newUser = Users.builder()
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .password(
                        passwordEncoder.encode(registerRequest.getPassword())
                )
                .createdAt(new Date())
                .build();

        Users savedUser = userRepository.save(newUser);

        // Generate token
        final String jwt = jwtUtil.generateToken(savedUser.getEmail(), savedUser.getId());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AuthResponse(jwt, savedUser.getId(), savedUser.getName(), savedUser.getEmail()));
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                String username = jwtUtil.extractUsername(token);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                if (jwtUtil.validateToken(token, userDetails)) {
                    Users user = userDetailsService.loadUserEntityByEmail(username);
                    return ResponseEntity.ok(new AuthResponse(token, user.getId(), user.getName(), user.getEmail()));
                }
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No token provided");
    }

}

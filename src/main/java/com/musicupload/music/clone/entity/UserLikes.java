package com.musicupload.music.clone.entity;

import jakarta.persistence.*;
import lombok.*;

/*
The likes linked with the user and the music_id
Denotes the user that are linked with the music_id
 */
@Entity
@Table(name = "user_likes", catalog = "music_details")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserLikes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "music_id")
    private Long musicId;

    @Column(name = "user_id")
    private Long userId;
}

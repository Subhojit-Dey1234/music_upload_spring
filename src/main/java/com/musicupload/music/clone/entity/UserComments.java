package com.musicupload.music.clone.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_comments", catalog = "music_details")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserComments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comments", length = 500, nullable = false)
    private String comments;

    @Column(name = "user_id")
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "music_id", nullable = false)
    private Musics musics;
}

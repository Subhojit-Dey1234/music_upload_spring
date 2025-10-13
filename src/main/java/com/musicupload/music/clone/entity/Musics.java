package com.musicupload.music.clone.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "MUSICS", catalog = "music_details")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Musics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "USER_ID", nullable = false)
    private Long userId;

    @Column(name = "NUMBER_OF_LIKES", nullable = false)
    @Builder.Default
    private Long numberOfLikes = 0L;

    @Column(name = "CREATED_AT", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false, insertable = false, updatable = false)
    private Users user;
}

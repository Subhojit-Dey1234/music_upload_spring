package com.musicupload.music.clone.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "musics", catalog = "music_details")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Musics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "number_of_likes", nullable = false)
    @Builder.Default
    private Long numberOfLikes = 0L;

    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @Builder.Default
    private Date createdAt = new Date();

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
    @JsonIgnore
    private Users user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "document_id", nullable = false)
    private Documents documents;
}

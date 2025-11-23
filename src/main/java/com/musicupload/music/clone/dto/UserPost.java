package com.musicupload.music.clone.dto;

import com.musicupload.music.clone.entity.Musics;
import com.musicupload.music.clone.entity.UserComments;
import lombok.Builder;
import lombok.Getter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Builder
public class UserPost {
    Long id;
    String name;
    String email;
    Date localDateTime;

    @Builder.Default
    List<Musics> musicsList = new ArrayList<>();

    @Builder.Default
    List<UserComments> comments = new ArrayList<>();
}

package com.musicupload.music.clone.dto;


import java.util.Date;

public interface UserBaseProjection {
    Long getId();
    String getName();
    String getEmail();
    Date getCreatedAt();
}

package com.makersacademy.acebook.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;


@Data
public class PostDto {
    private Long id;
    private String content;
    private String posterName;
    private Timestamp timePosted;
    private String image;
    private Long postLikesCount;
    private List<String> likedBy;
    private String profilePic;
    private Long posterId;

    public PostDto(Long id, String content, String posterName,
                      Timestamp timePosted, String image, Long postLikesCount, List<String> likedBy, String profilePic, Long posterId) {
        this.id = id;
        this.content = content;
        this.posterName = posterName;
        this.timePosted = timePosted;
        this.image = image;
        this.postLikesCount = postLikesCount;
        this.likedBy = likedBy;
        this.profilePic = profilePic;
        this.posterId = posterId;
    }
}



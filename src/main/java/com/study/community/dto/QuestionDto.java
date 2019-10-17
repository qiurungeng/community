package com.study.community.dto;

import com.study.community.model.User;
import lombok.Data;

@Data
public class QuestionDto {
    private String title;
    private String description;
    private Long gmtCreate;
    private Long gmtModified;
    private int creator;
    private int commentCount;
    private int viewCount;
    private int likeCount;
    private String tag;
    private User user;
}

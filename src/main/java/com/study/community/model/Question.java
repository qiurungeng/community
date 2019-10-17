package com.study.community.model;

import lombok.Data;

@Data
public class Question {
    private String title;
    private String description;
    private Long gmtCreate;
    private Long gmtModified;
    private int creator;
    private int commentCount;
    private int viewCount;
    private int likeCount;
    private String tag;

}

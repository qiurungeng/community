package com.study.community.dto;

import com.study.community.model.Comment;
import com.study.community.model.User;
import lombok.Data;

@Data
public class CommentDto extends Comment {
//    private Long id;
//    private Long parentId;
//    private Integer type;
//    private Long likeCount;
//    private Long gmtCreate;
//    private Long gmtModified;
//    private Integer commentator;
//    private String content;
//    private Integer commentCount;
    private User user;
}

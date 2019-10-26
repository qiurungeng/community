package com.study.community.mapper;

import com.study.community.model.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface CommentExtMapper {

    @Insert("insert into ")
    void insert(Comment record);

    @Update("update comment set COMMENT_COUNT=COMMENT_COUNT+1 where id=#{commentId}")
    void incCommentCount(long commentId);
}

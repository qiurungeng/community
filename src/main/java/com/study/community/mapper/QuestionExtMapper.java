package com.study.community.mapper;

import com.study.community.model.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface QuestionExtMapper {

    @Update("update question set VIEW_COUNT=VIEW_COUNT+1 where id=#{questionId}")
    int incView(int questionId);

    @Update("update question set COMMENT_COUNT=COMMENT_COUNT+1 where id=#{questionId}")
    void incComment(int questionId);
}

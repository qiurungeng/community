package com.study.community.mapper;

import com.study.community.model.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface QuestionExtMapper {

    @Update("update question set VIEW_COUNT=VIEW_COUNT+1 where id=#{questionId}")
    int incView(int questionId);

    @Update("update question set COMMENT_COUNT=COMMENT_COUNT+1 where id=#{questionId}")
    void incComment(int questionId);

    @Select("select * from question where id!=#{question.id} and tag regexp #{question.tag}")
    List<Question> seleteRelated(@Param("question") Question question);
}

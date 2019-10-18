package com.study.community.mapper;

import com.study.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("insert into question " +
            "(title,description,gmt_create,gmt_modified,creator,comment_count,view_count,like_count,tag) " +
            "values " +
            "(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{commentCount},#{viewCount}," +
            "#{likeCount},#{tag})")
    void create(Question question);

    @Select("select * from question limit #{offset},#{size}")
    List<Question> list(int offset, int size);

    @Select("select count(1) from question")
    int count();

    @Select("select * from question where creator=#{userId} limit #{offset},#{size}")
    List<Question> listForUser(int userId, int offset, int size);

    @Select("select count(1) from question where creator=#{userId}")
    int countByUserId(int userId);
}

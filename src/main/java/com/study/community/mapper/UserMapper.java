package com.study.community.mapper;

import com.study.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Insert("insert into user (name,account,token,gmt_modified,gmt_create) values" +
            " (#{name},#{accountId},#{token},#{gmtModified},#{gmtCreate})")
    void insert(User user);

    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);
}

package com.study.community.mapper;

import com.study.community.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Insert("insert into user (name,account,token,gmt_modified,gmt_create,avatar_url) values" +
            " (#{name},#{accountId},#{token},#{gmtModified},#{gmtCreate},#{avatarUrl})")
    void insert(User user);

    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);

    @Select("select * from user where id=#{id}")
    User findById(@Param("id") int id);

    @Update("update user set token=#{token} where id=#{id}")
    void updateToken(@Param("id")int id, @Param("token")String token);

    @Select("select * from user where account=#{accountId}")
    User findByAccountId(@Param("accountId")String accountId);
}

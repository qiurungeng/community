package com.study.community.service;

import com.study.community.mapper.UserMapper;
import com.study.community.model.User;
import com.study.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    public void createOrUpdate(User user){
        UserExample userExample=new UserExample();
        userExample.createCriteria().andAccountEqualTo(user.getAccount());
        User dbUser=userMapper.selectByExample(userExample).get(0);

        if (dbUser==null){
            user.setGmtModified(System.currentTimeMillis());
            user.setGmtModified(System.currentTimeMillis());
            userMapper.insert(user);
        }else {
            dbUser.setGmtModified(System.currentTimeMillis());  //更新修改时间
            dbUser.setAvatarUrl(user.getAvatarUrl());           //更新头像
            dbUser.setName(user.getName());                     //更新用户名
            dbUser.setToken(user.getToken());                   //更新cookie
            user.setId(dbUser.getId());
            UserExample example=new UserExample();
            example.createCriteria().andIdEqualTo(dbUser.getId());
            userMapper.updateByExample(dbUser,example);
        }
    }
}

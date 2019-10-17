package com.study.community.controller;

import com.study.community.dto.AccessTokenDto;
import com.study.community.dto.GithubUser;
import com.study.community.mapper.UserMapper;
import com.study.community.model.User;
import com.study.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    GithubProvider githubProvider;

    @Value("${github.client.id}")
    String client_id;

    @Value("${github.client.secret}")
    String client_secret;

    @Value("${github.redirect.uri}")
    String redirect_uri;

    @Autowired
    UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code")String code,
                           @RequestParam(name = "state")String state,
                           HttpServletRequest request,
                           HttpServletResponse response) {
        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setClient_id(client_id);
        accessTokenDto.setCode(code);
        accessTokenDto.setState(state);
        accessTokenDto.setClient_secret(client_secret);
        accessTokenDto.setRedirect_uri(redirect_uri);
        String accessToken = githubProvider.getAccessToken(accessTokenDto);
        GithubUser githubUser = githubProvider.getUser(accessToken);  //得到Github登录授权所得的Github用户信息

        if (githubUser!=null && githubUser.getId()!=null){

            String token=UUID.randomUUID().toString();  //随机生成新cookieID
            User dbUser=userMapper.findByAccountId(githubUser.getId()+"");  //查询数据库看有无该用户记录

            if(dbUser==null){
                //若该Github用户不存在于数据库当中，则为其新建用户数据并添加到数据库
                User user = new User();
                user.setToken(token);
                user.setName(githubUser.getName());
                user.setAccountId(""+githubUser.getId());
                user.setGmtCreate(System.currentTimeMillis());
                user.setGmtModified(user.getGmtCreate());
                user.setAvatarUrl(githubUser.getAvatar_url());
                userMapper.insert(user);
            }else {
                //数据库已有该Github用户的记录，则只需更新其cookie（即token）值
                userMapper.updateToken(dbUser.getId(),token);
            }

            // 登录成功，写入cookie和session
//            request.getSession().setAttribute("user",githubUser);
            response.addCookie(new Cookie("token",token));

            return "redirect:/";
        }else {
            return "redirect:/";
        }
    }
}

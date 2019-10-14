package com.study.community.controller;

import com.study.community.dto.AccessTokenDto;
import com.study.community.dto.GithubUser;
import com.study.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code")String code,
                           @RequestParam(name = "state")String state) {
        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setClient_id(client_id);
        accessTokenDto.setCode(code);
        accessTokenDto.setState(state);
        accessTokenDto.setClient_secret(client_secret);
        accessTokenDto.setRedirect_uri(redirect_uri);
        String accessToken = githubProvider.getAccessToken(accessTokenDto);
        GithubUser user = githubProvider.getUser(accessToken);
        System.out.println(user.getName());
        return "index";
    }
}

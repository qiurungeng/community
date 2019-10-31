package com.study.community.controller;

import com.study.community.dto.PaginationDto;
import com.study.community.dto.QuestionDto;
import com.study.community.mapper.UserMapper;
import com.study.community.model.User;
import com.study.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    UserMapper userMapper;

    @Autowired
    QuestionService questionService;

    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(name = "page",defaultValue = "1")Integer page,
                        @RequestParam(name = "size",defaultValue = "10")Integer size,
                        @RequestParam(name = "search",required = false)String search){

        PaginationDto paginationDto=questionService.list(search,page,size);
        model.addAttribute("pagination",paginationDto);
        model.addAttribute("search",search);
        return "index";
    }
}

package com.study.community.controller;

import com.study.community.dto.PaginationDto;
import com.study.community.model.User;
import com.study.community.service.NotificationService;
import com.study.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {
    @Autowired
    QuestionService questionService;
    @Autowired
    NotificationService notificationService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action")String action,
                          Model model,
                          HttpServletRequest request,
                          @RequestParam(name = "page",defaultValue = "1")Integer page,
                          @RequestParam(name = "size",defaultValue = "10")Integer size){

        User user=((User)request.getSession().getAttribute("user"));
        if (user==null){
            model.addAttribute("error","会话超时，请重新登录用户！");
        }else {
            if(action.equals("question")) {
                model.addAttribute("section","question");
                model.addAttribute("sectionName","我的提问");
                PaginationDto paginationDto = questionService.list(user.getId(), page, size);
                model.addAttribute("pagination", paginationDto);
            }else if ("replies".equals(action)){
                PaginationDto paginationDto=notificationService.list(user.getId(),page,size);
                model.addAttribute("section","replies");
                model.addAttribute("sectionName","消息回复");
                model.addAttribute("pagination",paginationDto);
            }
        }

        return "profile";
    }
}

package com.study.community.controller;

import com.study.community.dto.NotificationDto;
import com.study.community.enums.NotificationTypeEnum;
import com.study.community.exception.CustomizeErrorCode;
import com.study.community.exception.CustomizeException;
import com.study.community.model.Notification;
import com.study.community.model.User;
import com.study.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationController {
    @Autowired
    NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String profile(HttpServletRequest request, @PathVariable(name = "id")Long id){
        User user=(User)request.getSession().getAttribute("user");
        if (user==null){
            throw new CustomizeException(CustomizeErrorCode.NO_LOGIN);
        }
        NotificationDto notificationDto=notificationService.read(id,user);

        if (NotificationTypeEnum.REPLY_COMMENT.getType()==notificationDto.getType()||
            NotificationTypeEnum.REPLY_QUESTION.getType()==notificationDto.getType()){
            return "redirect:/question/"+notificationDto.getOuterid();
        }else {
            return "redirect:/";
        }
    }
}

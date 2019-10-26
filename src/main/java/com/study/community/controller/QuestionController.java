package com.study.community.controller;

import com.study.community.dto.CommentDto;
import com.study.community.dto.QuestionDto;
import com.study.community.enums.CommentTypeEnum;
import com.study.community.service.CommentService;
import com.study.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    QuestionService questionService;
    @Autowired
    CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id")int id, Model model){

        QuestionDto questionDto = questionService.getById(id);
        List<CommentDto> comments=commentService.listById(id, CommentTypeEnum.QUESTION);
        if (questionDto!=null){
            questionService.incView(id);
            questionDto.setViewCount(questionDto.getViewCount()+1);
        }
        model.addAttribute("question",questionDto);
        model.addAttribute("comments",comments);
        return "question";
    }
}

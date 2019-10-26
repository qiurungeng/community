package com.study.community.controller;

import com.study.community.cache.TagCache;
import com.study.community.dto.QuestionDto;
import com.study.community.mapper.QuestionMapper;
import com.study.community.model.Question;
import com.study.community.model.User;
import com.study.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {


    @Autowired
    QuestionService questionService;

    @GetMapping("/publish")
    public String publish(Model model) {
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id")Integer id,Model model){
        QuestionDto question=questionService.getById(id);
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("id",question.getId());
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam("title")String title,
            @RequestParam("description")String description,
            @RequestParam("tag")String tag,
            @RequestParam("id")Integer id,
            HttpServletRequest request,
            Model model){
        User user=(User)request.getSession().getAttribute("user");
        if(user==null){
            model.addAttribute("error","会话超时，请重新登录用户！");
            return "redirect:/";
        }
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        if (id!=null)question.setId(id);
        questionService.createOrUpdate(question);

        return "redirect:/";
    }
}

package com.study.community.service;

import com.study.community.dto.PaginationDto;
import com.study.community.dto.QuestionDto;
import com.study.community.mapper.QuestionMapper;
import com.study.community.mapper.UserMapper;
import com.study.community.model.Question;
import com.study.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    UserMapper userMapper;

    public PaginationDto list(Integer page, Integer size){

        int offset=size*(page-1);
        //得到所有问题
        List<Question> questions=questionMapper.list(offset,size);
        //设置分页方案信息
        PaginationDto paginationDto=setPaginationQuestions(questions);
        int totalCount=questionMapper.count();
        paginationDto.setPagination(totalCount,page,size);
        //返回分页方案
        return paginationDto;
    }

    public PaginationDto list(Integer userId, Integer page, Integer size) {
        int offset=size*(page-1);
        //得到特定用户的分页方案的所有问题
        List<Question> questions=questionMapper.listForUser(userId,offset,size);
        //设置分页方案信息
        PaginationDto paginationDto=setPaginationQuestions(questions);
        int totalCount=questionMapper.countByUserId(userId);
        paginationDto.setPagination(totalCount,page,size);
        //返回分页方案
        return paginationDto;
    }

    //设置分页方案中用于展示的内容
    private PaginationDto setPaginationQuestions(List<Question> questions){

        List<QuestionDto> questionDtoList=new ArrayList<>();
        PaginationDto paginationDto=new PaginationDto();

        for (Question question:questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDto questionDto=new QuestionDto();
            BeanUtils.copyProperties(question,questionDto);
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }

        paginationDto.setQuestions(questionDtoList);
        return paginationDto;
    }
}

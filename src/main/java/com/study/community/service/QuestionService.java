package com.study.community.service;

import com.study.community.dto.PaginationDto;
import com.study.community.dto.QuestionDto;
import com.study.community.mapper.QuestionMapper;
import com.study.community.mapper.UserMapper;
import com.study.community.model.Question;
import com.study.community.model.QuestionExample;
import com.study.community.model.User;
import org.apache.ibatis.session.RowBounds;
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
//        List<Question> questions=questionMapper.
//                selectByExampleWithRowbounds(new QuestionExample(),new RowBounds(offset,size));
        List<Question> questions=questionMapper.selectByExampleWithBLOBsWithRowbounds(new QuestionExample(),new RowBounds(offset,size));

        //设置分页方案信息
        PaginationDto paginationDto=setPaginationQuestions(questions);
        int totalCount=(int)questionMapper.countByExample(new QuestionExample());
        paginationDto.setPagination(totalCount,page,size);
        //返回分页方案
        return paginationDto;
    }

    public PaginationDto list(Integer userId, Integer page, Integer size) {
        int offset=size*(page-1);
        //得到特定用户的分页方案的所有问题
        QuestionExample allQuestions=new QuestionExample();
        allQuestions.createCriteria().andCreatorEqualTo(userId);
        List<Question> questions=questionMapper.
                selectByExampleWithBLOBsWithRowbounds(allQuestions,new RowBounds(offset,size));
//        for (Question question :
//                questions) {
//            System.out.println("【QuestionServer：User questions】:"+question.toString());
//        }
        //设置分页方案信息
        PaginationDto paginationDto=setPaginationQuestions(questions);
            //个人所有问题数
        QuestionExample questionCount=new QuestionExample();
        questionCount.createCriteria().andCreatorEqualTo(userId);
        int totalCount=(int)questionMapper.countByExample(questionCount);

        paginationDto.setPagination(totalCount,page,size);
        //返回分页方案
        return paginationDto;
    }

    //设置分页方案中用于展示的内容
    private PaginationDto setPaginationQuestions(List<Question> questions){

        List<QuestionDto> questionDtoList=new ArrayList<>();
        PaginationDto paginationDto=new PaginationDto();

        for (Question question:questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDto questionDto=new QuestionDto();
            BeanUtils.copyProperties(question,questionDto);
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }

        paginationDto.setQuestions(questionDtoList);
        return paginationDto;
    }

    public QuestionDto getById(int id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        User user=userMapper.selectByPrimaryKey(question.getCreator());

        QuestionDto questionDto=new QuestionDto();
        BeanUtils.copyProperties(question,questionDto);
        questionDto.setUser(user);
        return questionDto;
    }

    public void createOrUpdate(Question question) {
        if (question.getId()!=null){
            question.setGmtModified(System.currentTimeMillis());
            //questionMapper.update(question);
            questionMapper.updateByPrimaryKeySelective(question);
        }else {
            //创建新的问题
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(System.currentTimeMillis());
            questionMapper.insertSelective(question);
        }
    }

//    public Question getQuestionById(Integer id) {
//        Question question=questionMapper.getById(id);
//        return question;
//    }
}

package com.study.community.service;

import com.study.community.dto.PaginationDto;
import com.study.community.dto.QuestionDto;
import com.study.community.exception.CustomizeErrorCode;
import com.study.community.exception.CustomizeException;
import com.study.community.mapper.QuestionExtMapper;
import com.study.community.mapper.QuestionMapper;
import com.study.community.mapper.UserMapper;
import com.study.community.model.Question;
import com.study.community.model.QuestionExample;
import com.study.community.model.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    QuestionExtMapper questionExtMapper;

    public PaginationDto list(Integer page, Integer size){

        int offset=size*(page-1);
        //得到所有问题
//        List<Question> questions=questionMapper.
//                selectByExampleWithRowbounds(new QuestionExample(),new RowBounds(offset,size));
        QuestionExample questionExample=new QuestionExample();
        questionExample.setOrderByClause("gmt_create desc");
        List<Question> questions=questionMapper.selectByExampleWithBLOBsWithRowbounds(questionExample,new RowBounds(offset,size));

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
        allQuestions.setOrderByClause("gmt_create desc");
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
        if (question==null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
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
            int updated=questionMapper.updateByPrimaryKeySelective(question);
            if (updated!=1){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }else {
            //创建新的问题
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(System.currentTimeMillis());
            questionMapper.insertSelective(question);
        }
    }

    public void incView(int questionId){
        questionExtMapper.incView(questionId);
    }

    /**
     * 根据已有问题的标签选出相关问题
     * @param questionDto
     * @return
     */
    public List<QuestionDto> selectRelated(QuestionDto questionDto) {
        if (StringUtils.isBlank(questionDto.getTag())){
            return new ArrayList<>();
        }
        //提取查询用的id、Tag正则表达式
        String[] tags = StringUtils.split(questionDto.getTag(), "、");
        String regexpTag= Arrays.stream(tags).collect(Collectors.joining("|"));
        Question query=new Question();
        query.setId(questionDto.getId());
        query.setTag(regexpTag);
        //查询并将结果封装为dto
        List<Question> questions=questionExtMapper.seleteRelated(query);
        List<QuestionDto> results=questions.stream().map(q->{
            QuestionDto dto=new QuestionDto();
            BeanUtils.copyProperties(q,dto);
            return dto;
        }).collect(Collectors.toList());

        return results;
    }

//    public Question getQuestionById(Integer id) {
//        Question question=questionMapper.getById(id);
//        return question;
//    }
}

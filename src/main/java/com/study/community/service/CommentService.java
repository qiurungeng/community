package com.study.community.service;

import com.study.community.dto.CommentDto;
import com.study.community.enums.CommentTypeEnum;
import com.study.community.exception.CustomizeErrorCode;
import com.study.community.exception.CustomizeException;
import com.study.community.mapper.*;
import com.study.community.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class CommentService {
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    CommentExtMapper commentExtMapper;
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    QuestionExtMapper questionExtMapper;
    @Autowired
    UserMapper userMapper;

    @Transactional
    public void insert(Comment comment) {
        if (comment.getParentId()==null||comment.getParentId()==0){
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);    //评论无父ID
        }
        if (comment.getType()==null|| !CommentTypeEnum.isExits(comment.getType())){
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);      //评论类型错误
        }else if (comment.getType()==CommentTypeEnum.COMMENT.getType()){
            //回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (dbComment==null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);      //要回复的评论不存在
            }
            commentMapper.insert(comment);
            commentExtMapper.incCommentCount(comment.getParentId());
        }else {
            //回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId().intValue());
            if (question==null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);      //要回复的评论不存在
            }
            commentMapper.insert(comment);
            questionExtMapper.incComment(question.getId());
        }

    }

    public List<CommentDto> listById(int id, CommentTypeEnum type) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().
                andParentIdEqualTo((long)id).
                andTypeEqualTo(type.getType());
        commentExample.setOrderByClause("like_count desc");
        List<Comment> comments = commentMapper.selectByExampleWithBLOBs(commentExample);

        if (comments!=null&&comments.size()!=0){
            //获取去重的评论人
            Set<Integer> commentators=comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
            List<Integer> userIds=new ArrayList<>();
            userIds.addAll(commentators);

            //获取评论人并转化为Map
            UserExample userExample=new UserExample();
            userExample.createCriteria().andIdIn(userIds);
            List<User> users=userMapper.selectByExample(userExample);
            Map<Integer,User> userMap=users.stream().collect(Collectors.toMap(user -> user.getId(),user -> user));

            //转化comment为commentDto
            List<CommentDto> commentDtos=comments.stream().map(comment -> {
                CommentDto commentDto=new CommentDto();
                BeanUtils.copyProperties(comment,commentDto);
                commentDto.setUser(userMap.get(comment.getCommentator()));
                return commentDto;
            }).collect(Collectors.toList());

            return commentDtos;
        }
        return null;
    }
}

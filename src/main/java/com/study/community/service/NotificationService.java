package com.study.community.service;

import com.study.community.dto.NotificationDto;
import com.study.community.dto.PaginationDto;
import com.study.community.enums.NotificationStatusEnum;
import com.study.community.enums.NotificationTypeEnum;
import com.study.community.exception.CustomizeErrorCode;
import com.study.community.exception.CustomizeException;
import com.study.community.mapper.NotificationMapper;
import com.study.community.mapper.UserMapper;
import com.study.community.model.Notification;
import com.study.community.model.NotificationExample;
import com.study.community.model.User;
import com.study.community.model.UserExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class NotificationService {
    @Autowired
    NotificationMapper notificationMapper;
    @Autowired
    UserMapper userMapper;

    public PaginationDto list(Integer userId, Integer page, Integer size) {
        PaginationDto<NotificationDto> paginationDto=new PaginationDto<>();
        Integer totalPage;
        NotificationExample notificationExample=new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(userId.longValue());
//        notificationExample.setOrderByClause("gmt_create desc");
        Integer totalCount=(int)notificationMapper.countByExample(notificationExample);

        paginationDto.setPagination(totalCount,page,size);

        int offset=size*(page-1);
        NotificationExample example=new NotificationExample();
        example.createCriteria().andReceiverEqualTo(userId.longValue());
        example.setOrderByClause("gmt_create desc");

        List<Notification> notifications=notificationMapper.selectByExampleWithRowbounds(example,new RowBounds(offset,size));
        if (notifications.size()==0)return paginationDto;

//        Set<Integer> distinctUserIds = notifications.stream().map(notification -> notification.getNotifier().intValue()).collect(Collectors.toSet());
//        List<Integer> userIds = new ArrayList<>(distinctUserIds);
//        UserExample userExample=new UserExample();
//        userExample.createCriteria().andIdIn(userIds);
//        List<User> users = userMapper.selectByExample(userExample);
//        Map<Integer, User> userMap = users.stream().collect(Collectors.toMap(u -> u.getId(), u -> u));


        List<NotificationDto> notificationsDtos=new ArrayList<>();

        for (Notification notification:notifications){
            NotificationDto notificationDto=new NotificationDto();
            BeanUtils.copyProperties(notification,notificationDto);
            notificationDto.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationsDtos.add(notificationDto);
        }

        paginationDto.setData(notificationsDtos);

        //返回分页方案
        return paginationDto;
    }

    public Long unreadCount(Integer id) {
        NotificationExample notificationExample=new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(id.longValue())
                            .andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
        return notificationMapper.countByExample(notificationExample);
    }

    public NotificationDto read(Long id, User user) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if (notification==null){
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if (!Objects.equals(notification.getReceiver(), user.getId().longValue())){
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }
        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKey(notification);

        NotificationDto notificationDto=new NotificationDto();
        BeanUtils.copyProperties(notification,notificationDto);
        notificationDto.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
        return notificationDto;
    }
}

package com.study.community.exception;

public enum  CustomizeErrorCode implements ICustomizeErrorCode{

    QUESTION_NOT_FOUND(2001,"当前问题不在了唉，换一个试试？"),
    TARGET_PARAM_NOT_FOUND(2002,"未选中任何问题或评论进行回复"),
    NO_LOGIN(2003,"当前操作需要登录，请重试"),
    SYSTEM_ERROR(2004,"服务冒烟了，回家凉快去吧"),
    TYPE_PARAM_WRONG(2005,"评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2006,"你回复的评论不存在了。"),
    CONTENT_IS_EMPTY(2007,"评论不能为空"),
    READ_NOTIFICATION_FAIL(2008,"大哥你怎么读了别人的信息呢？"),
    NOTIFICATION_NOT_FOUND(2009,"当前通知不见了哎");
    private String message;
    private Integer code;


    @Override
    public String getMessage(){
        return message;
    }
    @Override
    public Integer getCode() {
        return code;
    }

    CustomizeErrorCode(Integer code,String message) {
        this.code=code;
        this.message=message;
    }
}

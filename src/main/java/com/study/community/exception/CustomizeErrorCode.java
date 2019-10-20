package com.study.community.exception;

public enum  CustomizeErrorCode implements ICustomizeErrorCode{

    QUESTION_NOT_FOUND("当前问题不在了唉，换一个试试？");

    private String message;

    @Override
    public String getMessage(){
        return message;
    }

    CustomizeErrorCode(String message) {
        this.message=message;
    }
}

package com.hxsd.exception;

/**
 * Created by jinhs on 2019-09-01.
 */
public enum CustomizeErrorCode implements ICustomizeErrorCode {

    QUESTION_NOT_FOUND("没有该问题了~，请确认单号");

    @Override
    public String getMessage(){
        return message;
    }

    private String message;

    CustomizeErrorCode(String message){
        this.message=message;
    }
}

package com.hxsd.exception;


/**
 * Created by jinhs on 2019-09-01.
 */
public enum CustomizeErrorCode implements ICustomizeErrorCode {

    QUESTION_NOT_FOUND(2001,"没有该问题了~，请确认单号"),
    TARGET_PARAM_NOT_FOUND(2002,"未选中任何问题进行评论"),
    NO_LOGIN(2003,"未登录不能进行评论，请先登入"),
    SYS_ERROR(2004,"服务出错了，请稍后再试~"),
    TYPE_PARAM_WRONG(2005,"评论类型错误"),
    COMMENT_NOT_FOUND(2006,"没有这个评论"),
    COMMENT_IS_EMPTY(2007,"输入内容不能为空");

    private Integer code;
    private String message;

    CustomizeErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage(){
        return message;
    }
}

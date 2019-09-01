package com.hxsd.exception;

/**
 * Created by jinhs on 2019-09-01.
 */
public class CustomizeException extends RuntimeException {
    private String message;

    public CustomizeException(ICustomizeErrorCode errorCode) {
        this.message = errorCode.getMessage();
    }

    /*public CustomizeException(String message){
        this.message = message;
    }*/

    @Override
    public String getMessage() {
        return message;
    }
}

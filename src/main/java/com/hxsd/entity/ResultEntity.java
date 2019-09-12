package com.hxsd.entity;

import com.hxsd.exception.CustomizeErrorCode;
import com.hxsd.exception.CustomizeException;
import lombok.Data;

/**
 * Created by jinhs on 2019-08-15.
 */
@Data
public class ResultEntity {

    private Integer code;
    private String message;

    public static ResultEntity errorOf(Integer code,String message){
        ResultEntity resultEntity = new  ResultEntity();
        resultEntity.setCode(code);
        resultEntity.setMessage(message);
        return resultEntity;
    }

    public static ResultEntity errorOf(CustomizeErrorCode errorCode) {
        return errorOf(errorCode.getCode(),errorCode.getMessage());
    }
    public static ResultEntity okOf() {
        ResultEntity resultEntity = new  ResultEntity();
        resultEntity.setCode(200);
        resultEntity.setMessage("请求成功");
        return resultEntity;
    }

    public static ResultEntity errorOf(CustomizeException e) {

        return errorOf(e.getCode(),e.getMessage());
    }
}

package com.hxsd.advice;

import com.hxsd.entity.ResultEntity;
import com.hxsd.exception.CustomizeErrorCode;
import com.hxsd.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by jinhs on 2019-09-01.
 */
@ControllerAdvice
public class CustomizeExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    Object handle(Throwable e, Model model,
                        HttpServletRequest request) {
        String contentType = request.getContentType();
        if("application/json".equals(contentType)){
            //json方式返回错误
            if(e instanceof CustomizeException){
                return ResultEntity.errorOf((CustomizeException) e);
            }else{
                return ResultEntity.errorOf(CustomizeErrorCode.SYS_ERROR);
            }
        }else{

            if(e instanceof CustomizeException){
                model.addAttribute("message",e.getMessage());
            }else{
                model.addAttribute("message",CustomizeErrorCode.SYS_ERROR.getMessage());
            }
            return new ModelAndView("error");
        }

    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}

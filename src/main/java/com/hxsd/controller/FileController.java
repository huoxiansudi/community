package com.hxsd.controller;

import com.hxsd.entity.FileEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: community
 * @Date: 2019-10-10 14:01
 * @Author: jinhs
 * @Description:
 */
@Controller
public class FileController {

    @RequestMapping("/file/upload")
    @ResponseBody
    public FileEntity upload(){

        FileEntity fileEntity = new FileEntity();
        fileEntity.setSuccess(1);
        fileEntity.setUrl("/img/wechat.png");

        return fileEntity;
    }
}

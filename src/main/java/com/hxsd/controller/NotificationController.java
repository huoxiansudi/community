package com.hxsd.controller;

import com.hxsd.entity.NotificationEntity;
import com.hxsd.enums.NotificationTypeEnum;
import com.hxsd.model.User;
import com.hxsd.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by jinhs on 2019/9/27.
 */
@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String profile(@PathVariable(name = "id") Long id,
                          Model model,
                          HttpServletRequest request,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "5") Integer size) {
        //判断用户是否登录（每一次访问会先通过使用拦截器判断，是否登录）
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }

        NotificationEntity notificationEntity = notificationService.read(id, user);

        if(NotificationTypeEnum.REPLY_COMMENT.getType()==notificationEntity.getType()
                || NotificationTypeEnum.REPLY_QUESTION.getType()==notificationEntity.getType()){

            return "redirect:/question/"+notificationEntity.getOuterid();
        }else {
            return "redirect:/";
        }


    }
}

package com.hxsd.controller;

import com.hxsd.entity.PaginationEntity;
import com.hxsd.model.User;
import com.hxsd.service.NotificationService;
import com.hxsd.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by jinhs on 2019-08-19.
 */
@Controller
public class ProfileController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action,
                          Model model,
                          HttpServletRequest request,
                          @RequestParam(name = "page",defaultValue = "1")Integer page,
                          @RequestParam(name = "size",defaultValue = "2")Integer size){
        //判断用户是否登录（每一次访问会先通过使用拦截器判断，是否登录）
        User user = (User) request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/";
        }

        if("questions".equals(action)){

            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");
            PaginationEntity paginationEntity = questionService.list(user.getId(), page, size);
            model.addAttribute("pagination",paginationEntity);
        }else if("replies".equals(action)){
            PaginationEntity paginationEntity = notificationService.list(user.getId(), page, size);
            model.addAttribute("pagination",paginationEntity);
            model.addAttribute("sectionName","最新回复");
            model.addAttribute("section","replies");

        }


        return "profile";
    }
}

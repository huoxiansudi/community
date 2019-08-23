package com.hxsd.controller;

import com.hxsd.entity.PaginationEntity;
import com.hxsd.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by jinhs on 2019-08-01.
 */
@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(name = "page",defaultValue = "1")Integer page,
                        @RequestParam(name = "size",defaultValue = "5")Integer size) {


        PaginationEntity pagination = questionService.list(page,size);
        model.addAttribute("pagination",pagination);
        return "index";
    }
}

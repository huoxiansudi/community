package com.hxsd.controller;

import com.hxsd.entity.CommentEntity;
import com.hxsd.entity.QuestionEntity;
import com.hxsd.enums.CommentTypeEnum;
import com.hxsd.service.CommentService;
import com.hxsd.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Created by jinhs on 2019-08-23.
 */
@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String profile(@PathVariable(name = "id") Long id,Model model){

        QuestionEntity questionEntity = questionService.getById(id);

        List<CommentEntity> commentEntities = commentService.listByTargetId(id, CommentTypeEnum.QUESTION);

        //累计阅读数
        questionService.incView(id);

        model.addAttribute("question",questionEntity);
        model.addAttribute("comments",commentEntities);

        return "question";
    }
}

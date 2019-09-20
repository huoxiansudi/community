package com.hxsd.controller;

import com.hxsd.entity.CommentEntity;
import com.hxsd.entity.QuestionEntity;
import com.hxsd.service.CommentService;
import com.hxsd.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
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
    public String profile(@PathVariable(name = "id") Long id,
                          Model model,
                          HttpServletRequest request,
                          @RequestParam(name = "page",defaultValue = "1")Integer page,
                          @RequestParam(name = "size",defaultValue = "2")Integer size){

        QuestionEntity questionEntity = questionService.getById(id);

        List<CommentEntity> commentEntities = commentService.listByQuestionId(id);

        //累计阅读数
        questionService.incView(id);

        model.addAttribute("question",questionEntity);
        model.addAttribute("comments",commentEntities);

        return "question";
    }
}

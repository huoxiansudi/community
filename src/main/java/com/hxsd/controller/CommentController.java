package com.hxsd.controller;

import com.hxsd.entity.CommentEntity;
import com.hxsd.entity.ResultEntity;
import com.hxsd.exception.CustomizeErrorCode;
import com.hxsd.mapper.CommentMapper;
import com.hxsd.model.Comment;
import com.hxsd.model.User;
import com.hxsd.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


/**
 * Created by jinhs on 2019-08-23.
 */
@Controller
public class CommentController {
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object post(@RequestBody CommentEntity commentEntity,
                       HttpServletRequest request){

        User user = (User) request.getSession().getAttribute("user");

        if(user==null){
            return ResultEntity.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
//
        Comment comment = new Comment();
        comment.setParentId(commentEntity.getParentId());
        comment.setContent(commentEntity.getContent());
        comment.setType(commentEntity.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0L);

        commentService.insert(comment);

        //对返回结果进行封装
        /*Map<Object,Object> objectObjectMap = new HashMap<>();
        objectObjectMap.put("message","成功");*/

        return ResultEntity.okOf();
    }
}

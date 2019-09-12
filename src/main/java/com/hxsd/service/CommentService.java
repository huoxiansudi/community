package com.hxsd.service;

import com.hxsd.enums.CommentTypeEnum;
import com.hxsd.exception.CustomizeErrorCode;
import com.hxsd.exception.CustomizeException;
import com.hxsd.mapper.CommentMapper;
import com.hxsd.mapper.QuestionExtMapper;
import com.hxsd.mapper.QuestionMapper;
import com.hxsd.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jinhs on 2019-08-15.
 */
@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;

    public void insert(Comment comment) {

        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }

        if (comment.getType() == null || !CommentTypeEnum.isExit(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }

        if(comment.getType() == CommentTypeEnum.COMMENT.getType()){
            //回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if(dbComment==null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);
        }else{
            //回复问题
            questionMapper.selectByPrimaryKey(Integer.parseInt(String.valueOf(comment.getParentId())));
        }
    }
}

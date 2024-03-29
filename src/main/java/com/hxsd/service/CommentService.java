package com.hxsd.service;

import com.hxsd.entity.CommentEntity;
import com.hxsd.enums.CommentTypeEnum;
import com.hxsd.enums.NotificationStatusEnum;
import com.hxsd.enums.NotificationTypeEnum;
import com.hxsd.exception.CustomizeErrorCode;
import com.hxsd.exception.CustomizeException;
import com.hxsd.mapper.*;
import com.hxsd.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
    @Autowired
    private CommentExtMapper commentExtMapper;
    @Autowired
    private NotificationMapper notificationMapper;
    @Autowired
    private UserMapper userMapper;

    @Transactional
    public void insert(Comment comment, User commentator) {

        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }

        if (comment.getType() == null || !CommentTypeEnum.isExit(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }

        if (comment.getType() == CommentTypeEnum.COMMENT.getType()) {
            //回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (dbComment == null) {
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            //回复问题
            Question question = questionMapper.selectByPrimaryKey(dbComment.getParentId());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }

            commentMapper.insert(comment);

            //增加评论数
            Comment parentComment = new Comment();
            parentComment.setId(comment.getParentId());
            parentComment.setCommentCount(1);
            commentExtMapper.updateCommentCount(parentComment);

            //创建通知
            createNotify(comment, dbComment.getCommentator(), commentator.getName(), question.getTitle(), question.getId(),NotificationTypeEnum.REPLY_COMMENT);

        } else {
            //回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            question.setCommentCount(1);
            questionExtMapper.updateCommentCount(question);
            //创建通知
            createNotify(comment, question.getCreator(),commentator.getName(),question.getTitle(),question.getId(),NotificationTypeEnum.REPLY_QUESTION);
        }
    }

    //创建通知方法
    private void createNotify(Comment comment, Long receiver, String notifierName, String outerTitle,Long outerId, NotificationTypeEnum notificationTypeEnum) {
        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());

        notification.setType(notificationTypeEnum.getType());
        notification.setOuterid(outerId);
        notification.setNotifier(comment.getCommentator());
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        notification.setReceiver(receiver);
        notification.setNotifierName(notifierName);
        notification.setOuterTitle(outerTitle);
        notificationMapper.insert(notification);
    }

    //指定缓存组件的名字，添加缓存的条件，当unless条件为true则不缓存
    //,condition = "#id>1",unless = "#result == null "
    @Cacheable(cacheNames = {"comment"})
    public List<CommentEntity> listByTargetId(Long id,CommentTypeEnum typeEnum) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria()
                .andParentIdEqualTo(id)
                .andTypeEqualTo(typeEnum.getType());
        //添加sql根据时间排序
        commentExample.setOrderByClause("gmt_create desc");
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        if (comments.size() == 0) {
            return new ArrayList<>();
        }
        //获取去重的评论人
        Set<Long> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());

        List<Long> userIds = new ArrayList<>();
        userIds.addAll(commentators);
        System.out.println("查询-----");
        //获取评论人并转换为map
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

        //转换 comment 为 commentEntity
        List<CommentEntity> commentEntities = comments.stream().map(comment -> {
            CommentEntity commentEntity = new CommentEntity();
            BeanUtils.copyProperties(comment, commentEntity);
            commentEntity.setUser(userMap.get(comment.getCommentator()));
            return commentEntity;

        }).collect(Collectors.toList());

        return commentEntities;
    }
}

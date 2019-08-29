package com.hxsd.service;

import com.hxsd.entity.PaginationEntity;
import com.hxsd.entity.QuestionEntity;
import com.hxsd.mapper.QuestionMapper;
import com.hxsd.mapper.UserMapper;
import com.hxsd.model.Question;
import com.hxsd.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jinhs on 2019-08-15.
 */
@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    public PaginationEntity list(Integer page, Integer size) {

        List<QuestionEntity> questionEntityList = new ArrayList<>();
        PaginationEntity paginationEntity = new PaginationEntity();

        //获取总数和分页数
        Integer totalCount = questionMapper.count();
        paginationEntity.setPagination(totalCount, page, size);
        //获取总数和分页数

        if (page < 1) {
            page = 1;
        }
        if (page > paginationEntity.getTotalPage()) {
            page = paginationEntity.getTotalPage();
        }

        //数据库分页查询
        Integer offset = size * (page - 1);
        List<Question> questions = questionMapper.list(offset, size);
        //数据库分页查询

        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());

            //把question的所有属性 拷贝到 qeustionTranEntity
            QuestionEntity questionEntity = new QuestionEntity();
            BeanUtils.copyProperties(question, questionEntity);
            questionEntity.setUser(user);
            questionEntity.setAvatarUrl(user.getAvatarUrl());
            questionEntityList.add(questionEntity);
        }

        paginationEntity.setQuestions(questionEntityList);


        return paginationEntity;

    }

    public PaginationEntity list(Integer userId, Integer page, Integer size) {
        List<QuestionEntity> questionEntityList = new ArrayList<>();
        PaginationEntity paginationEntity = new PaginationEntity();

        //获取总数和分页数
        Integer totalCount = questionMapper.countByUserId(userId);
        paginationEntity.setPagination(totalCount, page, size);
        //获取总数和分页数

        if (page <= 1) {
            page = 1;
        }
        if (page > paginationEntity.getTotalPage() && page!=1) {
            page = paginationEntity.getTotalPage();
        }

        //数据库分页查询
        Integer offset = size * (page - 1);
        List<Question> questions = questionMapper.replyList(userId,offset, size);
        //数据库分页查询

        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());

            //把question的所有属性 拷贝到 qeustionTranEntity
            QuestionEntity questionEntity = new QuestionEntity();
            BeanUtils.copyProperties(question, questionEntity);
            questionEntity.setUser(user);
            questionEntity.setAvatarUrl(user.getAvatarUrl());
            questionEntityList.add(questionEntity);
        }

        paginationEntity.setQuestions(questionEntityList);


        return paginationEntity;
    }

    public QuestionEntity getById(Integer id) {
        //把question的所有属性 拷贝到 qeustionTranEntity.
        QuestionEntity questionEntity = new QuestionEntity();
        Question question = questionMapper.getById(id);
        BeanUtils.copyProperties(question, questionEntity);
        User user = userMapper.findById(question.getCreator());
        questionEntity.setUser(user);

        return questionEntity;
    }

    public void createOrUpdate(Question question) {
        if(question.getId() == null){
            //创建
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(System.currentTimeMillis());
            questionMapper.create(question);
        }else{
            //更新
            question.setGmtModified(System.currentTimeMillis());
            questionMapper.update(question);
        }
    }
}

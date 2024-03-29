package com.hxsd.service;

import com.hxsd.entity.PaginationEntity;
import com.hxsd.entity.QuestionEntity;
import com.hxsd.exception.CustomizeErrorCode;
import com.hxsd.exception.CustomizeException;
import com.hxsd.mapper.QuestionExtMapper;
import com.hxsd.mapper.QuestionMapper;
import com.hxsd.mapper.UserMapper;
import com.hxsd.model.Question;
import com.hxsd.model.QuestionExample;
import com.hxsd.model.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by jinhs on 2019-08-15.
 */
@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;

    @Autowired
    private UserMapper userMapper;

    public PaginationEntity list(Integer page, Integer size) {


        PaginationEntity<QuestionEntity> paginationEntity = new PaginationEntity<>();

        //获取总数和分页数
        Integer totalCount = (int) questionMapper.countByExample(new QuestionExample());
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

        QuestionExample questionExample = new QuestionExample();
        questionExample.setOrderByClause("gmt_create desc");
        List<Question> questions = questionMapper.selectByExampleWithBLOBsWithRowbounds(questionExample, new RowBounds(offset, size));
        //数据库分页查询
        List<QuestionEntity> questionEntityList = new ArrayList<>();
        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());

            //把question的所有属性 拷贝到 qeustionTranEntity
            QuestionEntity questionEntity = new QuestionEntity();
            BeanUtils.copyProperties(question, questionEntity);
            questionEntity.setUser(user);
            questionEntity.setAvatarUrl(user.getAvatarUrl());
            questionEntityList.add(questionEntity);
        }

        paginationEntity.setData(questionEntityList);


        return paginationEntity;

    }

    public PaginationEntity list(Long userId, Integer page, Integer size) {

        PaginationEntity<QuestionEntity> paginationEntity = new PaginationEntity<>();

        //获取总数和分页数
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria()
                .andCreatorEqualTo(userId);
        Integer totalCount = (int) questionMapper.countByExample(questionExample);
        paginationEntity.setPagination(totalCount, page, size);
        //获取总数和分页数

        if (page <= 1) {
            page = 1;
        }
        if (page > paginationEntity.getTotalPage() && page != 1) {
            page = paginationEntity.getTotalPage();
        }

        //数据库分页查询
        Integer offset = size * (page - 1);
        QuestionExample example = new QuestionExample();
        example.createCriteria()
                .andCreatorEqualTo(userId);
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));
        //数据库分页查询

        List<QuestionEntity> questionEntityList = new ArrayList<>();
        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());

            //把question的所有属性 拷贝到 qeustionTranEntity
            QuestionEntity questionEntity = new QuestionEntity();
            BeanUtils.copyProperties(question, questionEntity);
            questionEntity.setUser(user);
            questionEntity.setAvatarUrl(user.getAvatarUrl());
            questionEntityList.add(questionEntity);
        }

        paginationEntity.setData(questionEntityList);


        return paginationEntity;
    }

    public QuestionEntity getById(Long id) {
        //把question的所有属性 拷贝到 qeustionTranEntity.
        QuestionEntity questionEntity = new QuestionEntity();

        Question question = questionMapper.selectByPrimaryKey(id);
        if (question == null) {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        BeanUtils.copyProperties(question, questionEntity);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        questionEntity.setUser(user);

        return questionEntity;
    }

    public void createOrUpdate(Question question) {
        if (question.getId() == null) {
            //创建
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(System.currentTimeMillis());
            question.setViewCount(0);
            question.setCommentCount(0);
            questionMapper.insert(question);
        } else {
            //更新
            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());
            QuestionExample questionExample = new QuestionExample();
            questionExample.createCriteria()
                    .andIdEqualTo(question.getId());
            int updateId = questionMapper.updateByExampleSelective(updateQuestion, questionExample);
            if (updateId != 1) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    public void incView(Long id) {
        Question updateQuestion = new Question();
        updateQuestion.setId(id);
        updateQuestion.setViewCount(1);

        int aa = questionExtMapper.updateViewCount(updateQuestion);

       /* QuestionExample questionExample =new  QuestionExample();
        questionExample.createCriteria()
                .andIdEqualTo(id);
        questionMapper.updateByExampleSelective(updateQuestion, questionExample);*/

    }

    public List<QuestionEntity> selectRelated(QuestionEntity queryEntity) {

        if(StringUtils.isBlank(queryEntity.getTag())){
            return new ArrayList<>();
        }
        String[] tags = StringUtils.split(queryEntity.getTag(), "，");
        String regexpTag = Arrays.stream(tags).collect(Collectors.joining("|"));
        Question question = new Question();
        question.setId(queryEntity.getId());
        question.setTag(regexpTag);
        List<Question> questionList = questionExtMapper.selectRelated(question);
        List<QuestionEntity> questionEntityList = questionList.stream().map(q -> {
            QuestionEntity questionEntity = new QuestionEntity();
            BeanUtils.copyProperties(q,questionEntity);
            return questionEntity;
        }).collect(Collectors.toList());

        return questionEntityList;
    }
}

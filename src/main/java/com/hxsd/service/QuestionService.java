package com.hxsd.service;

import com.hxsd.entity.QuestionEntity;
import com.hxsd.mapper.QuestionMapper;
import com.hxsd.mapper.UserMapper;
import com.hxsd.model.Question;
import com.hxsd.model.User;
import com.sun.javafx.scene.control.skin.VirtualFlow;
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

    public List<QuestionEntity> list() {

        List<Question> questions = questionMapper.list();
        List<QuestionEntity> questionEntityList = new ArrayList<>();

        for (Question question:questions) {
             User user = userMapper.findById(question.getCreator());

            //把question的所有属性 拷贝到 qeustionTranEntity
            QuestionEntity questionEntity = new QuestionEntity();
            BeanUtils.copyProperties(question,questionEntity);
            questionEntity.setUser(user);
            questionEntityList.add(questionEntity);
        }

        return questionEntityList;

    }
}

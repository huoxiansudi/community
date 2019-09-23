package com.hxsd.mapper;

import com.hxsd.model.Question;

import java.util.List;

/**
 * Created by jinhs on 2019/9/2.
 */
public interface QuestionExtMapper {

    int updateViewCount(Question question);
    int updateCommentCount(Question question);
    List<Question> selectRelated(Question question);
}

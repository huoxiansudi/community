package com.hxsd.entity;

import com.hxsd.model.User;
import lombok.Data;

/**
 * Created by jinhs on 2019-08-15.
 */
@Data
public class QuestionEntity {

    private Integer id;
    private Integer commentCount;
    private Integer creator;
    private Integer viewCount;
    private String title;
    private String tag;
    private String description;
    private Long gmtCreate;
    private Long gmtModified;
    private User user;
}

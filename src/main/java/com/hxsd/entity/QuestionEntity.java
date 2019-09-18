package com.hxsd.entity;

import com.hxsd.model.User;
import lombok.Data;

/**
 * Created by jinhs on 2019-08-15.
 */
@Data
public class QuestionEntity {

    private Long id;
    private Integer commentCount;
    private Long creator;
    private Integer viewCount;
    private String title;
    private String tag;
    private String description;
    private Long gmtCreate;
    private Long gmtModified;
    private String avatarUrl;
    private User user;
}

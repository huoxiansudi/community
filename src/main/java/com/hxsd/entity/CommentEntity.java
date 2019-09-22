package com.hxsd.entity;

import com.hxsd.model.User;
import lombok.Data;

/**
 * Created by jinhs on 2019/9/20.
 */
@Data
public class CommentEntity {

    private Long id;
    private Long parentId;
    private Integer type;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private Integer commentCount;
    private String content;
    private User user;
}

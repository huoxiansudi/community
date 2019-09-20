package com.hxsd.entity;

import lombok.Data;

/**
 * Created by jinhs on 2019-08-15.
 */
@Data
public class CommentCreateEntity {

    private Long parentId;
    private String content;
    private Integer type;
}

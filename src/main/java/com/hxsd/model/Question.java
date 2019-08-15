package com.hxsd.model;

import lombok.Data;

/**
 * Created by jinhs on 2019-08-06.
 */
@Data
public class Question {

    private Integer id;
    private Integer commentCount;
    private Integer creator;
    private Integer viewCount;
    private String title;
    private String tag;
    private String description;
    private Long gmtCreate;
    private Long gmtModified;

}

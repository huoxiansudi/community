package com.hxsd.model;

import lombok.Data;

/**
 * Created by jinhs on 2019-08-06.
 */
@Data
public class User {

    private Integer id;
    private String name;
    private String accountId;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
    private String avatarUrl;

}

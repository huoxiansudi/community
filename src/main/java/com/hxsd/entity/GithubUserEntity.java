package com.hxsd.entity;

import lombok.Data;

/**
 * Created by jinhs on 2019-08-04.
 */
@Data
public class GithubUserEntity {
    private String name;
    private Long id;
    private String bio;
    private String avatar_url;
}

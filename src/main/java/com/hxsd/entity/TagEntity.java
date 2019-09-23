package com.hxsd.entity;

import lombok.Data;

import java.util.List;

/**
 * Created by jinhs on 2019-08-15.
 */
@Data
public class TagEntity {

    private String categoryName;
    private List<String> tags;
}

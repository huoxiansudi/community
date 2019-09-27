package com.hxsd.entity;

import lombok.Data;

/**
 * Created by jinhs on 2019-09-24.
 */
@Data
public class NotificationEntity {
    private Long id;
    private Long gmtCreate;
    private Integer status;
    private Long notifier;
    private String notifierName;
    private String outerTitle;
    private Long outerid;
    private String typeName;
    private Integer type;
}

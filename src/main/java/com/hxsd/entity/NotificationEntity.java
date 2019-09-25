package com.hxsd.entity;

import com.hxsd.model.User;
import lombok.Data;

/**
 * Created by jinhs on 2019-09-24.
 */
@Data
public class NotificationEntity {
    private Long id;
    private Long gmt_create;
    private Integer status;
    private User notifier;
    private String outerTitle;
    private String type;
}

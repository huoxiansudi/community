package com.hxsd.entity;

import lombok.Data;

/**
 * Created by jinhs on 2019-08-04.
 */
@Data
public class AccessTokenEntity {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_url;
    private String state;

}

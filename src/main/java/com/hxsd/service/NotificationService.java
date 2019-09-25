package com.hxsd.service;

import com.hxsd.entity.PaginationEntity;
import com.hxsd.mapper.NotificationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jinhs on 2019-09-24.
 */
@Service
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    public PaginationEntity list(Long id, Integer page, Integer size) {
        return null;
    }
}

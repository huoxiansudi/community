package com.hxsd.service;

import com.hxsd.entity.NotificationEntity;
import com.hxsd.entity.PaginationEntity;
import com.hxsd.enums.NotificationStatusEnum;
import com.hxsd.enums.NotificationTypeEnum;
import com.hxsd.exception.CustomizeErrorCode;
import com.hxsd.exception.CustomizeException;
import com.hxsd.mapper.NotificationMapper;
import com.hxsd.model.Notification;
import com.hxsd.model.NotificationExample;
import com.hxsd.model.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by jinhs on 2019-09-24.
 */
@Service
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    public PaginationEntity list(Long userId, Integer page, Integer size) {

        PaginationEntity<NotificationEntity> paginationEntity = new PaginationEntity<>();

        //获取总数和分页数
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(userId);
        Integer totalCount = (int) notificationMapper.countByExample(notificationExample);
        paginationEntity.setPagination(totalCount, page, size);
        //获取总数和分页数

        if (page <= 1) {
            page = 1;
        }
        if (page > paginationEntity.getTotalPage() && page != 1) {
            page = paginationEntity.getTotalPage();
        }

        //数据库分页查询
        Integer offset = size * (page - 1);
        NotificationExample example = new NotificationExample();
        example.createCriteria()
                .andReceiverEqualTo(userId);
        example.setOrderByClause("gmt_create desc");
        List<Notification> notificationList = notificationMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));
        //数据库分页查询

        if (notificationList.size() == 0) {
            return paginationEntity;
        }

       /* Set<Long> disUserIds = notificationList.stream().map(notify -> notify.getNotifier()).collect(Collectors.toSet());
        List<Long> userIds = new ArrayList<>(disUserIds);
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(u -> u.getId(), u -> u));*/

        List<NotificationEntity> notificationEntityList = new ArrayList<>();
        for (Notification notification : notificationList) {

            //把question的所有属性 拷贝到 qeustionTranEntity
            NotificationEntity notificationEntity = new NotificationEntity();
            BeanUtils.copyProperties(notification, notificationEntity);
            notificationEntity.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationEntityList.add(notificationEntity);
        }

        paginationEntity.setData(notificationEntityList);
        return paginationEntity;
    }

    public Long unreadCount(Long id) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(id)
                .andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
        return notificationMapper.countByExample(notificationExample);
    }

    public NotificationEntity read(Long id, User user) {

        Notification notification = notificationMapper.selectByPrimaryKey(id);

        if (notification == null) {
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }

        if (!Objects.equals(notification.getReceiver(), user.getId())) {
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }

        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKeySelective(notification);

        NotificationEntity notificationEntity = new NotificationEntity();
        BeanUtils.copyProperties(notification, notificationEntity);
        notificationEntity.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
        return notificationEntity;

    }
}

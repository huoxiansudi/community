package com.hxsd.service;

import com.hxsd.mapper.UserMapper;
import com.hxsd.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jinhs on 2019-08-27.
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user) {
        User dbUser = userMapper.findByAccountId(user.getAccountId());
        if(dbUser == null){
            //插入数据
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }else{
            dbUser.setGmtCreate(System.currentTimeMillis());
            dbUser.setGmtModified(user.getGmtCreate());
            dbUser.setName(user.getName());
            dbUser.setToken(user.getToken());
            dbUser.setAvatarUrl(user.getAvatarUrl());
            //更新数据
            userMapper.update(dbUser);
        }
    }
}

package com.hxsd.service;

import com.hxsd.mapper.UserMapper;
import com.hxsd.model.User;
import com.hxsd.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jinhs on 2019-08-27.
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<User> userList = userMapper.selectByExample(userExample);
        if(userList.size() == 0){
            //插入数据
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }else{
            User dbUser = userList.get(0);

            User updateUser = new User();
            updateUser.setGmtCreate(System.currentTimeMillis());
            updateUser.setGmtModified(user.getGmtCreate());
            updateUser.setName(user.getName());
            updateUser.setToken(user.getToken());
            updateUser.setAvatarUrl(user.getAvatarUrl());
            UserExample example = new UserExample();
            example.createCriteria().andIdEqualTo(dbUser.getId());
            //更新数据
            userMapper.updateByExampleSelective(updateUser,example);
        }
    }
}

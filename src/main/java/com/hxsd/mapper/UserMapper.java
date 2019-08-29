package com.hxsd.mapper;

import com.hxsd.model.User;
import org.apache.ibatis.annotations.*;

/**
 * Created by jinhs on 2019-08-06.
 */
@Mapper
public interface UserMapper {

    @Insert("insert into user(name,account_id,token,gmt_create,gmt_modified,AVATAR_URL)values" +
            "(#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    void insert(User user);

    //
    @Select("select * from User where token=#{token}")
    User findByToken(@Param("token") String token);

    @Select("select * from user where id=#{id}")
    User findById(@Param("id") Integer id);

    @Select("select * from user where account_id=#{accountId}")
    User findByAccountId(@Param("accountId") String accountId);

    @Update("update user set name=#{name},token=#{token},gmt_modified=#{gmtModified},AVATAR_URL=#{avatarUrl} where id=#{id}")
    void update(User user);
}

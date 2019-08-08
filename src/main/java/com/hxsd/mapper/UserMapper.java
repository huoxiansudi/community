package com.hxsd.mapper;

import com.hxsd.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by jinhs on 2019-08-06.
 */
@Mapper
public interface UserMapper {

    @Insert("insert into user(name,account_id,token,gmt_create,gmt_modified)values" +
            "(#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified  })")
    void insert(User user);

}

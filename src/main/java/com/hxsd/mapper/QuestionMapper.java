package com.hxsd.mapper;

import com.hxsd.model.Question;
import com.hxsd.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * Created by jinhs on 2019-08-06.
 */
@Mapper
public interface QuestionMapper {

    @Insert("insert into question(title,description,tag,gmt_create,gmt_modified,creator)" +
            "values(#{title},#{description},#{tag},#{gmtCreate},#{gmtModified},#{creator})")
    void create(Question question);

   @Select("select * from User where token=#{token}")
   User findByToken(String token);
}

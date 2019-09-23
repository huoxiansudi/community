package com.hxsd.cache;

import com.hxsd.entity.TagEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jinhs on 2019-09-23.
 */
public class TagCache {
    public static List<TagEntity> get(){
        List<TagEntity> tagEntityList = new ArrayList<>();

        TagEntity program = new TagEntity();
        program.setCategoryName("开发语言");
        program.setTags(Arrays.asList("Java","js","css","c","html","python","ruby","active"));
        tagEntityList.add(program);

        TagEntity framework = new TagEntity();
        framework.setCategoryName("平台框架");
        framework.setTags(Arrays.asList("Spring Boot","js","css","c","html","python","ruby","active"));
        tagEntityList.add(framework);

        TagEntity server = new TagEntity();
        server.setCategoryName("服务器");
        server.setTags(Arrays.asList("Tomcat","js","css","c","html","python","ruby","active"));
        tagEntityList.add(framework);

        return tagEntityList;

    }
}

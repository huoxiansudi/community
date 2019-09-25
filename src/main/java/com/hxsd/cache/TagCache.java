package com.hxsd.cache;

import com.hxsd.entity.TagEntity;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by jinhs on 2019-09-23.
 */
public class TagCache {
    public static List<TagEntity> get(){
        List<TagEntity> tagEntityList = new ArrayList<>();

        TagEntity program = new TagEntity();
        program.setCategoryName("开发语言");
        program.setTags(Arrays.asList("Java","javascript","c#","python","Ruby","PHP","swift","basic","html5","node","asp.net","vue.js","angular 2","bootstrap","less/sass","ember.js"));
        tagEntityList.add(program);

        TagEntity framework = new TagEntity();
        framework.setCategoryName("平台框架");
        framework.setTags(Arrays.asList("Spring Boot","js","css","c","html","python","ruby","active"));
        tagEntityList.add(framework);

        TagEntity server = new TagEntity();
        server.setCategoryName("服务器");
        server.setTags(Arrays.asList("Tomcat","Linux","Windows","MacX","html","python"));
        tagEntityList.add(server);

        return tagEntityList;

    }

    public static String filterInvalid(String tags){

        String[] split = StringUtils.split(tags, "，");
        List<TagEntity> tagEntities = get();

        List<String> tagList = tagEntities.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());

        String invalid = Arrays.stream(split).filter(t -> !tagList.contains(t)).collect(Collectors.joining("，"));

        return invalid;

    }
}

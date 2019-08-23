package com.hxsd.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by jinhs on 2019-08-23.
 */
@Configuration
//@EnableWebMvc  (若开启静态页面使用不了，不开启可以使用)
public class WebConfig implements WebMvcConfigurer {

    private final Logger logger = LoggerFactory.getLogger(WebConfig.class);
    @Autowired
    private SessionInterceptor sessionInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        logger.info("add interceptors");

        //String[] excludes =new String[]{"/","/static/**"};
        registry.addInterceptor(sessionInterceptor).addPathPatterns("/**");
    }
}

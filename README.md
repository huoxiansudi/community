##资料
[Git]https://github.com/huoxiansudi/community
[Bootstrap]https://www.bootcss.com/

##工具
[登录git oauth]https://developer.github.com/apps/building-oauth-apps/authorizing-oauth-apps/

[访问地址]http:


##菜鸟教程

##Spring boot精髓
1）、springBoot启动会加载大量的自动配置类
2）、我们看我们需要的功能有没有默认写好的自动配置类
3）、我们再来看这个自动配置类中到底配置了那些组件；（只要我们要用的组件有，我们就不需要再来配置了）
4）、给容器中自动配置类添加组件的时候，会从properties类中获取某些属性。我们就可以在配置文件中指定这些属性的值；

xxxxAutoConfiguration:自动配置类；
给容器中添加组件
xxxxProperties:封装配置文件中相关属性；


````bash
mvn flyway:migrate
mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate

package com.hxsd.controller;

import com.hxsd.entity.AccessTokenEntity;
import com.hxsd.entity.GithubUserEntity;
import com.hxsd.mapper.UserMapper;
import com.hxsd.model.User;
import com.hxsd.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * Created by jinhs on 2019-08-03.
 */
@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;
    @Value("${github.client_id}")
    private String client_id;

    @Value("${github.client_secret}")
    private String client_secret;

    @Value("${github.redirect_url}")
    private String redirect_url;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response) {

        AccessTokenEntity accessTokenEntity = new AccessTokenEntity();
        accessTokenEntity.setClient_id(client_id);
        accessTokenEntity.setClient_secret(client_secret);
        accessTokenEntity.setCode(code);
        accessTokenEntity.setRedirect_url(redirect_url);
        accessTokenEntity.setState(state);

        String accessToken = githubProvider.accessToken(accessTokenEntity);
        GithubUserEntity githubUserEntity = githubProvider.getUser(accessToken);
        System.out.println(githubUserEntity.getName());

        if (githubUserEntity != null && githubUserEntity.getId()!=null) {

            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUserEntity.getName());
            user.setAccountId(String.valueOf(githubUserEntity.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            user.setAvatarUrl(githubUserEntity.getAvatar_url());
            userMapper.insert(user);//添加到数据库
            //request.getSession().setAttribute("user",githubUserEntity);
            response.addCookie(new Cookie("token",token));
            return "redirect:/";
            //登录成功 cookie 和 session
        } else {
            //登录失败 重新登录
            return "redirect:/";
        }
    }
}

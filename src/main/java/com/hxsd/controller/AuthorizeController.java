package com.hxsd.controller;

import com.hxsd.entity.AccessTokenEntity;
import com.hxsd.entity.GithubUserEntity;
import com.hxsd.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

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

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request) {

        AccessTokenEntity accessTokenEntity = new AccessTokenEntity();
        accessTokenEntity.setClient_id(client_id);
        accessTokenEntity.setClient_secret(client_secret);
        accessTokenEntity.setCode(code);
        accessTokenEntity.setRedirect_url(redirect_url);
        accessTokenEntity.setState(state);
        String accessToken = githubProvider.accessToken(accessTokenEntity);
        GithubUserEntity user = githubProvider.getUser(accessToken);
        System.out.println(user.getName());

        if (user != null) {

            request.getSession().setAttribute("user",user);
            return "redirect:/";
            //登录成功
        } else {
            //登录失败
            return "redirect:/";
        }
    }
}

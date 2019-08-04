package com.hxsd.provider;

import com.alibaba.fastjson.JSON;
import com.hxsd.entity.AccessTokenEntity;
import com.hxsd.entity.GithubUserEntity;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by jinhs on 2019-08-03.
 */
/*@Component: 将当前类初始化到上下文，不用new生成实例化对象。
*
* */
@Component
public class GithubProvider {

    public String accessToken(AccessTokenEntity accessTokenEntity) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();


        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenEntity));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            System.out.println(string);
            String token = string.split("&")[0].split("=")[1];
            return token;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public GithubUserEntity getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();


        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accessToken)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String ss = response.body().string();
            System.out.println(ss);
            GithubUserEntity githubUserEntity = JSON.parseObject(ss, GithubUserEntity.class);
            return githubUserEntity;
        } catch (IOException e) {

        }
        return null;
    }
}

package com.hxsd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by jinhs on 2019/9/30.
 */
@Controller
public class WeixinLoginController {

    @GetMapping("/weixinLogin")
    public String WeixinLogin(){
        String url = "https://open.weixin.qq.com/connect/qrconnect?" +
                "appid=wx7287a60bb700fd21" +
                "&redirect_uri=http://www.txjava.cn/loginServlet" +
                "&response_type=code" +
                "&scope=snsapi_login&state=STATE#wechat_redirect";
        return "redirect:"+url;
    }
}

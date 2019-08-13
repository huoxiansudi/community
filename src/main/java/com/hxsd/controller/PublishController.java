package com.hxsd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by jinhs on 2019-08-12.
 */
@Controller
public class PublishController {
   @GetMapping("/publish")
    public String publish(){
       return "publish";
   }
}

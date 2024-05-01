package com.babyTalk.main.controller;

import com.babyTalk.base.annotation.UnLogin;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

public class PageController {
//    @RequestMapping("/2d")
//    @UnLogin
//    public String page2d(){
//        return "2d/index.html";
//    }
    @RequestMapping("/backstage")
    @UnLogin
    public String pageBackstage(){
        return "fsbackground/index.html";
    }
}

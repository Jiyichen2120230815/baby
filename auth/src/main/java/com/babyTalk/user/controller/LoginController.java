package com.babyTalk.user.controller;

import com.babyTalk.base.annotation.UnLogin;
import com.babyTalk.base.http.ResponseData;
import com.babyTalk.user.entity.vo.LoginVO;
import com.babyTalk.user.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private LoginService loginService;

    /**
     * 前台登录
     * @param loginVO
     * @return ResponseData<Object>
     */
    @UnLogin
    @PostMapping
    public ResponseData<Object> login(@RequestBody @Validated LoginVO loginVO){
        return loginService.login(loginVO);
    }
}

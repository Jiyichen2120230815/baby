package com.babyTalk.user.service;

import com.babyTalk.base.http.ResponseData;
import com.babyTalk.user.entity.vo.LoginVO;

public interface LoginService {
    ResponseData<Object> login(LoginVO loginVO);
}

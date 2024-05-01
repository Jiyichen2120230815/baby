package com.babyTalk.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.babyTalk.base.http.ResponseData;
import com.babyTalk.base.http.ResponseStatus;
import com.babyTalk.base.http.Result;
import com.babyTalk.base.utils.WrapperUtil;
import com.babyTalk.user.entity.User;
import com.babyTalk.user.entity.vo.LoginVO;
import com.babyTalk.user.mapper.UserMapper;
import com.babyTalk.user.service.LoginService;
import com.babyTalk.user.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JwtUtils jwtUtils;


    /**
     * 前台登录
     * @param loginVO
     * @return ResponseData<Object>
     */
    @Override
    public ResponseData<Object> login(LoginVO loginVO){
        final User user = userMapper.selectOne(WrapperUtil.getQueryWrapper("username", loginVO.getUsername()));
        if (user == null) {
            return Result.error(ResponseStatus.USERNAME_NOT_EXIST.getMessage(),ResponseStatus.USERNAME_NOT_EXIST.getStatus());
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(loginVO.getPassword(), user.getPassword())) {
            return Result.error(ResponseStatus.PASSWORD_IS_ERROR.getMessage(),ResponseStatus.PASSWORD_IS_ERROR.getStatus());
        }
        //获取用户所有权限
//        final List<Integer> authorities = getAuthorities(user);
        final List<Integer> authorities = new ArrayList<>();
        authorities.add(1);
        //获取token
        final String accessToken = jwtUtils.getAccessToken(user.getUsername(), authorities);
//        System.out.println(11111);
        final String refreshToken = jwtUtils.getRefreshToken(user.getId().toString(),authorities);
        //单点登录
        userMapper.updateById(user.setToken(accessToken));
        return Result.success(accessToken,refreshToken,user);
    }


//

}

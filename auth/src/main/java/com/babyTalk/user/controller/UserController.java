package com.babyTalk.user.controller;


import com.alibaba.fastjson.JSON;
import com.babyTalk.baby.entity.Baby;
import com.babyTalk.base.annotation.UnLogin;
import com.babyTalk.base.http.ResponseData;
import com.babyTalk.base.http.Result;
import com.babyTalk.user.entity.User;
import com.babyTalk.user.entity.vo.UserPageVO;
import com.babyTalk.user.entity.vo.UserVO;
import com.babyTalk.user.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cst
 * @since 2021-09-09
 */
@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;


    /**
     * 获取所有用户
     * @param current
     * @param size
     * @param user
     * @return
     */
    @GetMapping
    public ResponseData<Object> getAll1(@RequestParam(defaultValue = "1") Integer current,
                                        @RequestParam(defaultValue = "10") Integer size,
                                        User user){
        return userService.getAll1(current, size, user);
    }



















//
//    /**
//     * 获取所有用户
//     * 用户角色搜索
//     * 用户名搜索
//     * 用户昵称搜索
//     * @param userPageVO
//     * @return ResponseData<Object>
//     */
//    @GetMapping
//    public ResponseData<Object> getAll(UserPageVO userPageVO){
//        return userService.getAll(userPageVO);
//    }

    /**
     * 添加用户
     * @param user 用户
     * @return ResponseData<Object>
     */
    @PostMapping
    @UnLogin
    public ResponseData<Object> postUser(@RequestBody @Validated(User.insert.class) User user){
        return userService.postUser(user);
    }

    /**
     * 修改用户
     * @param user 用户
     * @return ResponseData<Object>
     */
    @PutMapping
    public ResponseData<Object> putUser(@RequestBody @Validated(User.update.class) User user){
        return userService.putUser(user);
    }

    /**
     * 删除用户
     * @param id id
     * @return ResponseData<Object>
     */
    @DeleteMapping
    public ResponseData<Object> delUser(@RequestParam Integer id){
        return userService.delUser(id);
    }

}


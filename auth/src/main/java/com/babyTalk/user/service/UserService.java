package com.babyTalk.user.service;

import com.babyTalk.base.http.ResponseData;
import com.babyTalk.user.entity.User;
import com.babyTalk.user.entity.vo.UserPageVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cst
 * @since 2021-09-09
 */
public interface UserService extends IService<User> {

    ResponseData<Object> getAll1(Integer current, Integer size, User user);

//    ResponseData<Object> getAll(UserPageVO userPageVO);

    ResponseData<Object> postUser(User user);

    ResponseData<Object> putUser(User user);

    ResponseData<Object> delUser(Integer id);
}

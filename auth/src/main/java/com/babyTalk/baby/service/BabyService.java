package com.babyTalk.baby.service;

import com.babyTalk.baby.entity.Baby;
import com.babyTalk.baby.entity.VO.BabyPageVO;
import com.babyTalk.base.http.ResponseData;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cst
 * @since 2021-09-09
 */
public interface BabyService extends IService<Baby> {

    ResponseData<Object> getAll1(Integer current, Integer size, Baby baby);

    ResponseData<Object> postBaby1(Baby baby);

    ResponseData<Object> putBaby1(Baby baby);

    ResponseData<Object> delBaby1(Integer id);

    ResponseData<Object> getAll(BabyPageVO babyPageVO);

    ResponseData<Object> postBaby(Baby baby, HttpServletRequest request);

    ResponseData<Object> putBaby(Baby baby);

    ResponseData<Object> delBaby(Integer id, HttpServletRequest request);
}

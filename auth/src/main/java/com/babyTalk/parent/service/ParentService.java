package com.babyTalk.parent.service;

import com.babyTalk.base.http.ResponseData;
import com.babyTalk.parent.entity.Parent;
import com.babyTalk.parent.entity.VO.ParentPageVO;
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
public interface ParentService extends IService<Parent> {

    ResponseData<Object> getAll1(Integer current, Integer size, Parent parent);

    ResponseData<Object> postParent1(Parent parent);

    ResponseData<Object> putParent1(Parent parent);

    ResponseData<Object> delParent1(Integer id);

    ResponseData<Object> checkParent(Integer id);

    ResponseData<Object> getAll(ParentPageVO parentPageVO);

    ResponseData<Object> postParent(Parent parent, HttpServletRequest request);

    ResponseData<Object> putParent(Parent parent);

    ResponseData<Object> delParent(Integer id, HttpServletRequest request);
}

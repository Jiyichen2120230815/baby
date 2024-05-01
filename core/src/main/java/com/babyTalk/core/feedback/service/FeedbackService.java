package com.babyTalk.core.feedback.service;

import com.babyTalk.base.http.ResponseData;
import com.babyTalk.core.feedback.entity.Feedback;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cst
 * @since 2021-10-16
 */
public interface FeedbackService extends IService<Feedback> {

    ResponseData<Object> getAll(Feedback feedback, Integer current, Integer size);

    ResponseData<Object> postFeedback(Feedback feedback);

    ResponseData<Object> putFeedback(Feedback feedback);

    ResponseData<Object> delFeedback(Integer id);
}

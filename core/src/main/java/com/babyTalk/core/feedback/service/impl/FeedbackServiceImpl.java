package com.babyTalk.core.feedback.service.impl;

import com.babyTalk.base.http.ResponseData;
import com.babyTalk.base.http.ResponseStatus;
import com.babyTalk.base.http.Result;
import com.babyTalk.base.utils.WrapperUtil;
import com.babyTalk.core.feedback.entity.Feedback;
import com.babyTalk.core.feedback.mapper.FeedbackMapper;
import com.babyTalk.core.feedback.service.FeedbackService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.PortUnreachableException;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cst
 * @since 2021-10-16
 */
@Service
public class FeedbackServiceImpl extends ServiceImpl<FeedbackMapper, Feedback> implements FeedbackService {
    @Autowired
    private FeedbackMapper feedbackMapper;

    @Override
    public ResponseData<Object> getAll(Feedback feedback,Integer current,Integer size){
        Page<Feedback> page = new Page<>(current,size);
        final IPage<Feedback> feedbackIPage = feedbackMapper.selectPage(page, WrapperUtil.queryWrapper(feedback));
        return Result.success(feedbackIPage);
    }

    @Override
    public ResponseData<Object> postFeedback(Feedback feedback){
        final int i = feedbackMapper.insert(feedback);
        if (i > 0) {
            return Result.success();
        }else {
            return Result.error(ResponseStatus.POST_ERROR.getMessage(),ResponseStatus.POST_ERROR.getStatus());
        }
    }

    @Override
    public ResponseData<Object> putFeedback(Feedback feedback){
        final int i = feedbackMapper.updateById(feedback);
        if (i > 0) {
            return Result.success();
        }else {
            return Result.error(ResponseStatus.PUT_ERROR.getMessage(),ResponseStatus.PUT_ERROR.getStatus());
        }
    }

    @Override
    public ResponseData<Object> delFeedback(Integer id){
        final int i = feedbackMapper.deleteById(id);
        if (i > 0) {
            return Result.success();
        }else {
            return Result.error(ResponseStatus.DELETE_ERROR.getMessage(),ResponseStatus.DELETE_ERROR.getStatus());
        }

    }




}

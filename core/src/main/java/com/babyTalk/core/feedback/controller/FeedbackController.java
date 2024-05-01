package com.babyTalk.core.feedback.controller;


import com.babyTalk.base.http.ResponseData;
import com.babyTalk.base.http.ResponseStatus;
import com.babyTalk.base.http.Result;
import com.babyTalk.base.utils.WrapperUtil;
import com.babyTalk.core.feedback.entity.Feedback;
import com.babyTalk.core.feedback.service.FeedbackService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cst
 * @since 2021-10-16
 */
@CrossOrigin
@RestController
@RequestMapping("/feedback")
public class FeedbackController {
    @Autowired
    private FeedbackService feedbackService;

    @GetMapping
    public ResponseData<Object> getAll(@RequestParam(defaultValue = "1") Integer current,
                                       @RequestParam(defaultValue = "10")  Integer size,
                                       Feedback feedback){
        return feedbackService.getAll(feedback, current, size);
    }

    @PostMapping
    public ResponseData<Object> postFeedback(@RequestBody @Validated(Feedback.insert.class) Feedback feedback){
        return feedbackService.postFeedback(feedback);
    }

    @PutMapping
    public ResponseData<Object> putFeedback(@RequestBody @Validated(Feedback.update.class) Feedback feedback){
        return feedbackService.putFeedback(feedback);
    }

    @DeleteMapping
    public ResponseData<Object> delFeedback(@RequestParam Integer id){
        return feedbackService.delFeedback(id);
    }

}


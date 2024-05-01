package com.babyTalk.core.classification.service;

import com.babyTalk.base.http.ResponseData;
import com.babyTalk.core.classification.entity.Classification;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cst
 * @since 2021-10-02
 */
public interface ClassificationService extends IService<Classification> {

    ResponseData<Object> getAll();

    ResponseData<Object> postClassification(Classification classification);

    ResponseData<Object> putClassification(Classification classification);

    ResponseData<Object> delClassification(Integer id);
}

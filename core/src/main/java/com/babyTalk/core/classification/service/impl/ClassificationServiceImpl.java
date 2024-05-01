package com.babyTalk.core.classification.service.impl;

import com.babyTalk.base.http.ResponseData;
import com.babyTalk.base.http.Result;
import com.babyTalk.core.classification.entity.Classification;
import com.babyTalk.core.classification.mapper.ClassificationMapper;
import com.babyTalk.core.classification.service.ClassificationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.util.ObjectBuffer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cst
 * @since 2021-10-02
 */
@Service
public class ClassificationServiceImpl extends ServiceImpl<ClassificationMapper, Classification> implements ClassificationService {
    @Autowired
    private ClassificationMapper classificationMapper;

    /**
     * 获取所有分类
     */
    @Override
    public ResponseData<Object> getAll(){
        final List<Classification> classifications = classificationMapper.selectList(null);
        return Result.success(classifications);
    }

    /**
     * 添加分类
     * @param classification
     */
    @Override
    public ResponseData<Object> postClassification(Classification classification){
        final int i = classificationMapper.insert(classification);
        if (i > 0) {
            return Result.success();
        }else {
            return Result.error();
        }
    }

    /**
     * 修改分类
     * @param classification
     */
    @Override
    public ResponseData<Object> putClassification(Classification classification){
        final int i = classificationMapper.updateById(classification);
        if (i > 0) {
            return Result.success();
        }else {
            return Result.error();
        }
    }

    /**
     * 删除分类
     * @param id
     */
    @Override
    public ResponseData<Object> delClassification(Integer id){
        final int i = classificationMapper.deleteById(id);
        if (i > 0) {
            return Result.success();
        }else {
            return Result.error();
        }
    }

}

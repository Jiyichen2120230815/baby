package com.babyTalk.core.classification.controller;


import com.babyTalk.base.http.ResponseData;
import com.babyTalk.base.http.Result;
import com.babyTalk.core.classification.entity.Classification;
import com.babyTalk.core.classification.service.ClassificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cst
 * @since 2021-10-02
 */
@CrossOrigin
@RestController
@RequestMapping("/classification")
public class ClassificationController {
    @Autowired
    private ClassificationService classificationService;

    /**
     * 获取所有分类
     */
    @GetMapping
    public ResponseData<Object> getAll(){
        return classificationService.getAll();
    }

    /**
     * 添加分类
     * @param classification
     */
    @PostMapping
    public ResponseData<Object> postClassification(@RequestBody @Validated(Classification.insert.class) Classification classification){
        return classificationService.postClassification(classification);
    }

    /**
     * 修改分类
     * @param classification
     */
    @PutMapping
    public ResponseData<Object> putClassification(@RequestBody @Validated(Classification.update.class) Classification classification){
        return classificationService.putClassification(classification);
    }

    /**
     * 删除分类
     * @param id
     */
    @DeleteMapping
    public ResponseData<Object> delClassification(@RequestParam Integer id){
        return classificationService.delClassification(id);
    }

}


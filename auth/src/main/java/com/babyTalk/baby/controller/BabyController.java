package com.babyTalk.baby.controller;


import com.babyTalk.baby.entity.Baby;
import com.babyTalk.baby.entity.VO.BabyPageVO;
import com.babyTalk.baby.service.BabyService;
import com.babyTalk.base.http.ResponseData;
import com.babyTalk.base.http.Result;
import com.babyTalk.parent.entity.Parent;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
@RequestMapping("/baby")
public class BabyController {
    @Autowired
    private BabyService babyService;


    /**
     * 获取所有宝宝
     * 名字搜索
     * 分页
     * @param baby
     */
    @GetMapping
    public ResponseData<Object> getAll1(@RequestParam(defaultValue = "1") Integer current,
                                        @RequestParam(defaultValue = "10") Integer size,
                                        Baby baby){
        return babyService.getAll1(current, size, baby);
    }

    /**
     * 添加宝宝
     * @param baby
     */
    @PostMapping
    public ResponseData<Object> postBaby1(@RequestBody @Validated(Baby.insert.class) Baby baby){
        return babyService.postBaby1(baby);
    }

    /**
     * 修改宝宝
     * @param baby
     */
    @PutMapping
    public ResponseData<Object> putBaby1(@RequestBody @Validated(Baby.update.class) Baby baby){
        return babyService.putBaby1(baby);
    }

    /**
     * 删除宝宝
     * @param id
     */
    @DeleteMapping
    public ResponseData<Object> delBaby1(@RequestParam Integer id){
        return babyService.delBaby1(id);
    }





//
//
//    /**
//     * 获取所有宝宝
//     * 名字搜索
//     * 分页
//     * @param babyPageVO
//     */
//    @GetMapping
//    public ResponseData<Object> getAll(BabyPageVO babyPageVO){
//        return babyService.getAll(babyPageVO);
//    }
//
//    /**
//     * 添加宝宝
//     * @param baby
//     */
//    @PostMapping
//    public ResponseData<Object> postBaby(@RequestBody @Validated(Baby.insert.class) Baby baby, HttpServletRequest request){
//        return babyService.postBaby(baby,request);
//    }
//
//    /**
//     * 修改宝宝
//     * @param baby
//     */
//    @PutMapping
//    public ResponseData<Object> putBaby(@RequestBody @Validated(Baby.update.class) Baby baby){
//        return babyService.putBaby(baby);
//    }
//
//    /**
//     * 删除宝宝
//     * @param id
//     */
//    @DeleteMapping
//    public ResponseData<Object> delBaby(@RequestParam Integer id,HttpServletRequest request){
//        return babyService.delBaby(id,request);
//    }



}


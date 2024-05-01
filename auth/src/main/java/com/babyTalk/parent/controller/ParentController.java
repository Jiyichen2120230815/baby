package com.babyTalk.parent.controller;


import com.babyTalk.base.http.ResponseData;
import com.babyTalk.base.http.ResponseStatus;
import com.babyTalk.base.http.Result;
import com.babyTalk.base.utils.WrapperUtil;
import com.babyTalk.parent.entity.Parent;
import com.babyTalk.parent.entity.VO.ParentPageVO;
import com.babyTalk.parent.service.ParentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

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
@RequestMapping("/parent")
public class ParentController {
    @Autowired
    private ParentService parentService;




    @GetMapping
    public ResponseData<Object> getAll1(@RequestParam(defaultValue = "1") Integer current,
                                        @RequestParam(defaultValue = "10") Integer size,
                                        Parent parent){
        return parentService.getAll1(current, size, parent);
    }
    @PostMapping
    public ResponseData<Object> postParent1(@RequestBody @Validated(Parent.insert.class) Parent parent){
        return parentService.postParent1(parent);
    }
    @PutMapping
    public ResponseData<Object> putParent1(@RequestBody @Validated(Parent.update.class) Parent parent){
        return parentService.putParent1(parent);
    }
    @DeleteMapping
    public ResponseData<Object> delParent1(@RequestParam Integer id){
        return parentService.delParent1(id);
    }

    @GetMapping("/check")
    public ResponseData<Object> check(@RequestParam Integer id){
        return parentService.checkParent(id);
    }






//
//    /**
//     * 获取所有父母
//     * 分页
//     * 父母名字搜索
//     * @param parentPageVO
//     */
//    @GetMapping
//    public ResponseData<Object> getAll(ParentPageVO parentPageVO){
//        return parentService.getAll(parentPageVO);
//    }
//
//    /**
//     * 添加父母
//     * @param parent
//     */
//    @PostMapping
//    public ResponseData<Object> postParent(@RequestBody @Validated(Parent.insert.class) Parent parent,HttpServletRequest request){
//        return parentService.postParent(parent,request);
//    }
//
//    /**
//     * 修改父母
//     * @param parent
//     */
//    @PutMapping
//    public ResponseData<Object> putParent(@RequestBody @Validated(Parent.update.class) Parent parent){
//        return parentService.putParent(parent);
//    }
//
//    /**
//     * 删除父母
//     * @param id
//     */
//    @DeleteMapping
//    public ResponseData<Object> delParent(@RequestParam Integer id, HttpServletRequest request){
//        return parentService.delParent(id,request);
//    }

}


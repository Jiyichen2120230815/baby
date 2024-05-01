package com.babyTalk.core.cryTranslateWords.controller;


import com.babyTalk.base.http.ResponseData;
import com.babyTalk.base.http.Result;
import com.babyTalk.base.utils.WrapperUtil;
import com.babyTalk.core.cryTranslateWords.entity.CryTranslateWords;
import com.babyTalk.core.cryTranslateWords.service.CryTranslateWordsService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cst
 * @since 2021-10-15
 */
@CrossOrigin
@RestController
@RequestMapping("/cryTranslateWords")
public class CryTranslateWordsController {
    @Autowired
    private CryTranslateWordsService cryTranslateWordsService;




    @PostMapping("/picture")
    public ResponseData<Object> testFormData_http_picture(MultipartFile file) throws IOException {
        return cryTranslateWordsService.testFormData_http_picture(file);
    }



    @PostMapping("/file")
    public ResponseData<Object> testFormData_http(MultipartFile file) throws IOException {
        return cryTranslateWordsService.testFormData_http(file);
    }

    /**
     * 获取所有翻译文案
     * @param current
     * @param size
     * @param cryTranslateWords
     * @return
     */
    @GetMapping
    public ResponseData<Object> getAll(@RequestParam(defaultValue = "1") Integer current,
                                       @RequestParam(defaultValue = "10") Integer size,
                                       CryTranslateWords cryTranslateWords){
        return cryTranslateWordsService.getAll(current, size, cryTranslateWords);
    }

    /**
     * 更新翻译文案
     * @param cryTranslateWords
     * @return
     */
    @PutMapping
    public ResponseData<Object> putCryTranslateWords(@RequestBody @Validated(CryTranslateWords.update.class) CryTranslateWords cryTranslateWords){
        return cryTranslateWordsService.putCryTranslateWords(cryTranslateWords);
    }

}


package com.babyTalk.core.cryTranslateWords.service;

import com.babyTalk.base.http.ResponseData;
import com.babyTalk.core.cryTranslateWords.entity.CryTranslateWords;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cst
 * @since 2021-10-15
 */
public interface CryTranslateWordsService extends IService<CryTranslateWords> {

    ResponseData<Object> testFormData_http_picture(MultipartFile file) throws IOException;

    //通过传递文件参数  mp3
    ResponseData<Object> testFormData_http(MultipartFile file) throws IOException;

    ResponseData<Object> getAll(Integer current, Integer size, CryTranslateWords cryTranslateWords);

    ResponseData<Object> putCryTranslateWords(CryTranslateWords cryTranslateWords);
}

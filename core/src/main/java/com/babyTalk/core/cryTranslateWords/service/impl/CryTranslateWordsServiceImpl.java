package com.babyTalk.core.cryTranslateWords.service.impl;

import com.babyTalk.base.http.ResponseData;
import com.babyTalk.base.http.ResponseStatus;
import com.babyTalk.base.http.Result;
import com.babyTalk.base.http.httpclient.util.HttpFileUtils;
import com.babyTalk.base.utils.WrapperUtil;
import com.babyTalk.core.cryTranslate.mapper.CryTranslateMapper;
import com.babyTalk.core.cryTranslateWords.entity.CryTranslateWords;
import com.babyTalk.core.cryTranslateWords.mapper.CryTranslateWordsMapper;
import com.babyTalk.core.cryTranslateWords.service.CryTranslateWordsService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cst
 * @since 2021-10-15
 */
@Service
public class CryTranslateWordsServiceImpl extends ServiceImpl<CryTranslateWordsMapper, CryTranslateWords> implements CryTranslateWordsService {
    @Autowired
    private CryTranslateWordsMapper cryTranslateWordsMapper;
//
//    /**
//     * 下拉菜单
//     * @return
//     */
//    public ResponseData<Object> wordList(){
//        final List<CryTranslateWords> cryTranslateWords = cryTranslateWordsMapper.selectList(null);
//        List<String> wordList = new ArrayList<>();
//        for (CryTranslateWords cryTranslateWord : cryTranslateWords) {
//            wordList.add(cryTranslateWord.getCryChinese());
//        }
//
//    }



    //通过传递文件参数  pcture
    /**
     * 根据图片返回是否抑郁
     * @param file
     * @return
     * @throws IOException
     */
    @Override
    public ResponseData<Object> testFormData_http_picture(MultipartFile file) throws IOException {
        //调用接口获取返回的判断结果
        String url = "http://127.0.0.1:8003/detect";//访问链接
        String answer = null;
        final String path = "staticFile" + System.getProperty("file.separator")
                + "detect_picture" + System.getProperty("file.separator");
//                + file.getOriginalFilename();
        try {
            answer = HttpFileUtils.getResponseEntityBody(file, url,path);
        }catch (Exception e){
            return Result.error(ResponseStatus.PARAM_IS_ERROR.getMessage(),ResponseStatus.PARAM_IS_ERROR.getStatus());
        }

        //返回对应文案和翻译
        final CryTranslateWords cry_word = cryTranslateWordsMapper.selectOne(WrapperUtil.getQueryWrapper("cry_word", answer));
        if (cry_word == null) {
            return Result.success(ResponseStatus.RETURN_IS_ERROR.getMessage(),ResponseStatus.RETURN_IS_ERROR.getStatus());
        }

        //设置返回对象
        Map<String,Object> res = new HashMap<>();
        res.put("word",answer);
        res.put("cryWord",cry_word);
        res.put("path",path);
        return Result.success(res);
    }



    //通过传递文件参数  mp3
    /**
     * 根据音频返回宝宝状态
     * @param file
     * @return
     * @throws IOException
     */
    @Override
    public ResponseData<Object> testFormData_http(MultipartFile file) throws IOException {
        //调用接口获取返回的判断结果
        String url = "http://127.0.0.1:8003/infer";//访问链接
        String answer = null;
        final String path = "staticFile" + System.getProperty("file.separator")
                + "infer_audio" + System.getProperty("file.separator");
//                + file.getOriginalFilename();

        try {
            answer = HttpFileUtils.getResponseEntityBody(file, url,path);
        }catch (Exception e){
            return Result.error(ResponseStatus.PARAM_IS_ERROR.getMessage(),ResponseStatus.PARAM_IS_ERROR.getStatus());
        }

        //返回对应文案和翻译
        final CryTranslateWords cry_word = cryTranslateWordsMapper.selectOne(WrapperUtil.getQueryWrapper("cry_word", answer));
        if (cry_word == null) {
            return Result.success(ResponseStatus.RETURN_IS_ERROR.getMessage(),ResponseStatus.RETURN_IS_ERROR.getStatus());
        }

        //设置返回对象
        Map<String,Object> res = new HashMap<>();
        res.put("word",answer);
        res.put("cryWord",cry_word);
        res.put("path",path);
        return Result.success(res);
    }


    /**
     * 获取所有翻译文案
     * @param current
     * @param size
     * @param cryTranslateWords
     * @return
     */
    @Override
    public ResponseData<Object> getAll(Integer current,Integer size,CryTranslateWords cryTranslateWords){
        Page<CryTranslateWords> page = new Page<>(current,size);
        final IPage<CryTranslateWords> cryTranslateWordsIPage = cryTranslateWordsMapper.selectPage(page, WrapperUtil.queryWrapper(cryTranslateWords));
        return Result.success(cryTranslateWordsIPage);
    }

    /**
     * 更新翻译文案
     * @param cryTranslateWords
     * @return
     */
    @Override
    public ResponseData<Object> putCryTranslateWords(CryTranslateWords cryTranslateWords){
        final int i = cryTranslateWordsMapper.updateById(cryTranslateWords);
        if (i > 0) {
            return Result.success();
        }else {
            return Result.error(ResponseStatus.PUT_ERROR.getMessage(),ResponseStatus.PUT_ERROR.getStatus());
        }
    }


}

package com.babyTalk.core.article.controller;


import com.babyTalk.base.http.ResponseData;
import com.babyTalk.base.http.Result;
import com.babyTalk.core.article.entity.Article;
import com.babyTalk.core.article.entity.VO.ArticleEditVO;
import com.babyTalk.core.article.entity.VO.ArticlePageVO;
import com.babyTalk.core.article.entity.VO.ArticleVO;
import com.babyTalk.core.article.service.ArticleService;
import com.babyTalk.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    /**
     * 获取所有文章
     * @return ResponseData<Object>
     */
    @GetMapping
    public ResponseData<Object> getAll(ArticlePageVO articlePageVO){
        return articleService.getAll(articlePageVO);
    }
    /**
     * 添加文章
     * @param articleEditVO
     */
    @PostMapping
    public ResponseData<Object> postArticle(@RequestBody @Validated(ArticleEditVO.insert.class) ArticleEditVO articleEditVO, HttpServletRequest request){
        return articleService.postArticle(articleEditVO, request);
    }

    /**
     * 修改文章
     * @param articleEditVO
     */
    @PutMapping
    public ResponseData<Object> putArticle(@RequestBody @Validated(ArticleEditVO.update.class) ArticleEditVO articleEditVO){
       return articleService.putArticle(articleEditVO);
    }

    /**
     * 删除文章
     * @param id
     */
    @DeleteMapping
    public ResponseData<Object> delArticle(@RequestParam Integer id){
        return articleService.delArticle(id);
    }



}


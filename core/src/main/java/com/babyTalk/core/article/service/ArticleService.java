package com.babyTalk.core.article.service;

import com.babyTalk.base.http.ResponseData;
import com.babyTalk.core.article.entity.Article;
import com.babyTalk.core.article.entity.VO.ArticleEditVO;
import com.babyTalk.core.article.entity.VO.ArticlePageVO;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cst
 * @since 2021-09-09
 */
public interface ArticleService extends IService<Article> {

    ResponseData<Object> getAll(ArticlePageVO articlePageVO);

    ResponseData<Object> postArticle(ArticleEditVO articleEditVO, HttpServletRequest request);

    ResponseData<Object> putArticle(ArticleEditVO articleEditVO);

    ResponseData<Object> delArticle(Integer id);
}

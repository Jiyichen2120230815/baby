package com.babyTalk.core.article.mapper;

import com.babyTalk.core.article.entity.Article;
import com.babyTalk.core.article.entity.VO.ArticlePageVO;
import com.babyTalk.core.article.entity.VO.ArticleVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author cst
 * @since 2021-09-09
 */
@Component
public interface ArticleMapper extends BaseMapper<Article> {

    IPage<ArticleVO> getAllArticlePage(Page<?> page,ArticlePageVO articlePageVO);
}

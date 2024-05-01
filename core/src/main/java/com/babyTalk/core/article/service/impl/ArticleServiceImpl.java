package com.babyTalk.core.article.service.impl;

import com.babyTalk.base.http.ResponseData;
import com.babyTalk.base.http.Result;
import com.babyTalk.base.utils.WrapperUtil;
import com.babyTalk.core.article.entity.Article;
import com.babyTalk.core.article.entity.VO.ArticleEditVO;
import com.babyTalk.core.article.entity.VO.ArticlePageVO;
import com.babyTalk.core.article.entity.VO.ArticleVO;
import com.babyTalk.core.article.mapper.ArticleMapper;
import com.babyTalk.core.article.service.ArticleService;
import com.babyTalk.core.articleClassification.entity.ArticleClassification;
import com.babyTalk.core.articleClassification.mapper.ArticleClassificationMapper;
import com.babyTalk.core.classification.entity.Classification;
import com.babyTalk.core.classification.mapper.ClassificationMapper;
import com.babyTalk.user.entity.User;
import com.babyTalk.user.utils.JwtUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cst
 * @since 2021-09-09
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private ArticleClassificationMapper articleClassificationMapper;
    @Autowired
    private ClassificationMapper classificationMapper;
    @Autowired
    private JwtUtils jwtUtils;


    /**
     * 获取所有文章
     * 分页
     * 文章标题 文章内容  昵称  分类
     * @param articlePageVO
     * @return ResponseData<Object>
     */
    @Override
    public ResponseData<Object> getAll(ArticlePageVO articlePageVO){
        Page<ArticleVO> page = new Page<>();
        if (articlePageVO.getCurrent() != null && articlePageVO.getSize() != null) {
            page = new Page<>(articlePageVO.getCurrent(),articlePageVO.getSize());
        }
        final IPage<ArticleVO> allArticle = articleMapper.getAllArticlePage(page,articlePageVO);
        for (ArticleVO record : allArticle.getRecords()) {
            //关联表集合
            final List<ArticleClassification> articleClassificationList = articleClassificationMapper.selectList(WrapperUtil.getQueryWrapper("article_id", record.getId()));
            //获取分类id集合
            List<Integer> classificationIds = new ArrayList<>();
            for (ArticleClassification articleClassification : articleClassificationList) {//通过关联表集合，在分类表中获取每一个分类的名称
                final Integer classificationId = articleClassification.getClassificationId();
                classificationIds.add(classificationId);
            }
            //根据id集合获取分类
            List<Classification> classifications = new ArrayList<>();
            if (classificationIds.size()!=0){
                QueryWrapper<Classification> queryWrapper = new QueryWrapper<>();
                queryWrapper.in("id",classificationIds);
                classifications = classificationMapper.selectList(queryWrapper);
            }
            record.setClassificationList(classifications);//设置分类集合
        }
        return Result.success(allArticle);
    }

    /**
     * 添加文章
     * @param articleEditVO
     */
    @Override
    public ResponseData<Object> postArticle(ArticleEditVO articleEditVO, HttpServletRequest request){
        System.out.println(articleEditVO.getClassificationIds());
        final User loginUser = jwtUtils.getLoginUserByRequest(request);
        articleEditVO.setUserId(loginUser.getId());
        Article article = new Article();
        BeanUtils.copyProperties(articleEditVO,article);

        final int i = articleMapper.insert(article);
        //设置关联表  添加文章的分类
        if (articleEditVO.getClassificationIds() != null && articleEditVO.getClassificationIds().size() != 0) {
            association(articleEditVO,article);
        }

        if (i > 0) {
            return Result.success();
        }else {
            return Result.error();
        }
    }

    /**
     * 修改文章
     * @param articleEditVO
     */
    @Override
    public ResponseData<Object> putArticle(ArticleEditVO articleEditVO){
        Article article = new Article();
        BeanUtils.copyProperties(articleEditVO,article);
        final int i = articleMapper.updateById(article);
        //分类关联表操作
        if (articleEditVO.getClassificationIds() != null && articleEditVO.getClassificationIds().size() != 0) {
            association(articleEditVO,article);
        }
        if (i > 0) {
            return Result.success();
        }else {
            return Result.error();
        }
    }

    /**
     * 删除文章
     * @param id
     */
    @Override
    public ResponseData<Object> delArticle(Integer id){
        final int i = articleMapper.deleteById(id);
        if (i > 0) {
            return Result.success();
        }else {
            return Result.error();
        }
    }


    /**
     * 文章分类关联表操作
     * 添加修改
     * @param articleEditVO
     * @param article
     */
    private void association(ArticleEditVO articleEditVO,Article article){
        //删除原有类型数据
        if (article.getId() != null) {
            QueryWrapper<ArticleClassification> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("article_id",article.getId());
            articleClassificationMapper.delete(queryWrapper);
        }
        //添加更新数据
        //设置关联表  添加文章的分类
        for (Integer classificationId : articleEditVO.getClassificationIds()) {
            ArticleClassification articleClassification = new ArticleClassification();
            articleClassification.setArticleId(article.getId());
            articleClassification.setClassificationId(classificationId);
            articleClassificationMapper.insert(articleClassification);
        }
    }


}

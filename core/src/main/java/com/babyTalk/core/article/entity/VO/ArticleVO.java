package com.babyTalk.core.article.entity.VO;

import com.babyTalk.core.classification.entity.Classification;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class ArticleVO {
    /**
     * 文章id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 发布文章的作者id
     */
    private Integer userId;

    /**
     * 发布文章的作者id
     */
    private String nickname;

    /**
     * 文章分类id集合
     */
    private List<Integer> classificationIds;

    /**
     * 文章分类集合
     */
    private List<Classification> classificationList;

    /**
     * 创建时间
     */
    private Timestamp createdAt;

    /**
     * 修改时间
     */
    private Timestamp updatedAt;

}

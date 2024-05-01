package com.babyTalk.core.article.entity.VO;

import lombok.Data;

import java.util.List;

@Data
public class ArticlePageVO {
    private Integer current;
    private Integer size;
    private String title;
    private String content;
    private String nickname;
    private String classification;
    private Integer id;
//    private List<Integer>
}

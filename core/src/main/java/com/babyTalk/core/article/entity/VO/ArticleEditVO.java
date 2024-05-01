package com.babyTalk.core.article.entity.VO;

import com.babyTalk.core.article.entity.Article;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

@Data
public class ArticleEditVO {
    public interface insert{}
    public interface update{}

    private static final long serialVersionUID=1L;

    /**
     * 文章id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @NotNull(message = "id不能为空",groups = {update.class})
    private Integer id;

    /**
     * 标题
     */
    @NotNull(message = "标题不能为空",groups = {insert.class})
    private String title;

    /**
     * 文章内容
     */
    @NotBlank(message = "文章内容不能为空",groups = {insert.class})
    private String content;

    /**
     * 发布文章的作者id
     */
    private Integer userId;

    /**
     * 文章分类id集合
     */
//    @JsonIgnore
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Integer> classificationIds;

    /**
     * 是否删除
     */
    @TableLogic
    private Boolean isDeleted;

    /**
     * 创建时间
     */
    private Timestamp createdAt;

    /**
     * 修改时间
     */
    private Timestamp updatedAt;


}

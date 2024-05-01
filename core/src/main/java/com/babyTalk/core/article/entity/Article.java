package com.babyTalk.core.article.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.sql.Timestamp;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.extern.java.Log;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 
 * </p>
 *
 * @author cst
 * @since 2021-09-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Article implements Serializable {

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

//    /**
//     * 文章分类id集合
//     */
////    @JsonIgnore
////    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
//    private String classificationIds;

    /**
     * 是否删除
     */
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

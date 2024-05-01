package com.babyTalk.parent.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.sql.Timestamp;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
public class Parent implements Serializable {

    public interface insert{}
    public interface update{}

    private static final long serialVersionUID=1L;

    /**
     * 父母的id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @NotNull(message = "id不能为空",groups = {update.class})
    private Integer id;

    /**
     * 父母的名称
     */
    @NotBlank(message = "name父母名称不能为空",groups = {insert.class})
    private String name;

    /**
     * 身高
     */
    private Double height;

    /**
     * 体重
     */
    private Double weight;

    /**
     * 血型
     */
    private String bloodType;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 头像
     */
    private String headImg;

    /**
     * 角色（爸爸或者妈妈
     */
    @NotBlank(message = "role角色（爸爸或者妈妈)不能为空",groups = {insert.class})
    private String role;

    /**
     * 是否删除
     * (被关联，不能删除软删除）
     */
    @TableLogic
    private Boolean isDeleted;

    /**
     * 创建时间
     */
    private Timestamp createdAt;

    /**
     * 更新时间
     */
    private Timestamp updatedAt;

    /**
     * 绑定用户id
     */
    private Integer userId;


}

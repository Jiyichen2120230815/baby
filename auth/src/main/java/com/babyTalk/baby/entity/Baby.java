package com.babyTalk.baby.entity;

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
public class Baby implements Serializable {

    public interface insert{}
    public interface update{}

    private static final long serialVersionUID=1L;

    /**
     * 宝宝id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @NotNull(message = "id不能为空",groups = {update.class})
    private Integer id;

    /**
     * 姓名
     */
    @NotBlank(message = "姓名不能为空",groups = {insert.class})
    private String name;

    /**
     * 身高
     */
    @NotNull(message = "身高不能为空",groups = {insert.class})
    private Double height;

    /**
     * 体重
     */
    @NotNull(message = "体重不能为空",groups = {insert.class})
    private Double weight;

    /**
     * 血型
     */
    @NotBlank(message = "血型不能为空",groups = {insert.class})
    private String bloodType;

    /**
     * 年龄(月)
     */
    @NotNull(message = "年龄(月)不能为空",groups = {insert.class})
    private Integer age;

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
     * 修改时间
     */
    private Timestamp updatedAt;

    /**
     * 绑定用户id
     */
    private Integer userId;


}

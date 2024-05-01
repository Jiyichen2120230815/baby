package com.babyTalk.user.entity;

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
public class User implements Serializable {

    public interface insert{}
    public interface update{}

    private static final long serialVersionUID=1L;

    /**
     * 用户id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @NotNull(message = "id不能为空",groups = {update.class})
    private Integer id;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空",groups = {insert.class})
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空",groups = {insert.class})
    private String password;

    /**
     * 昵称
     */
    @NotBlank(message = "昵称不能为空",groups = {insert.class})
    private String nickname;

    /**
     * 爸爸的id
     */
    private Integer fatherId;

    /**
     * 妈妈的id
     */
    private Integer motherId;

    /**
     * 宝宝的id集合
     */
    private String babyIds;

    /**
     * 单点登录token
     */
    private String token;

    /**
     * 角色（1管理员 2普通用户
     */
    @NotNull(message = "角色不能为空",groups = {insert.class})
    private Integer role;

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


}

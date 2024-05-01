package com.babyTalk.user.entity.vo;

import com.babyTalk.baby.entity.Baby;
import com.babyTalk.parent.entity.Parent;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class UserVO {
    /**
     * 用户id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickname;

//    /**
//     * 爸爸的id
//     */
//    private Integer fatherId;
//
//    /**
//     * 爸爸对象
//     */
//    private Parent parentFather;
//
//    /**
//     * 妈妈的id
//     */
//    private Integer motherId;
//
//    /**
//     * 妈妈的对象
//     */
//    private Parent parentMother;

    /**
     * 宝宝的id集合
     */
    private List<Integer> babyIds;

    /**
     * 宝宝对象集合集合
     */
    private List<Baby> babyList;

    /**
     * 角色（1管理员 2普通用户
     */
    private Integer role;

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


    /**
     * 父母对象集合
     */
    private List<Parent> parentList;

}

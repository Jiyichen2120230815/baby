package com.babyTalk.base.http;

import lombok.Getter;

@Getter
public enum ResponseStatus {
    SUCCESS("操作成功",200),
    USERNAME_IS_EXIST("用户名已存在",101),
    USERNAME_NOT_EXIST("用户名不存在",102),
    PARAM_IS_ERROR("请求参数错误",103),
    USERNAME_IS_NULL("用户名不能为空",104),
    PASSWORD_IS_NULL("密码不能为空",105),
    ID_IS_NULL("id不能为空",106),
    TOKEN_NOT_PROVIDE("未传入token",107),
    TOKEN_IS_ERROR("token错误",108),
    TOKEN_IS_EXPIRED("token已过期",109),
    TOKEN_NOT_EXIST("token失效，请重新登录",110),
    PASSWORD_IS_ERROR("密码错误",111),
    WITHOUT_PERMISSION("未授权",112),
    PHONE_IS_EXIST("手机号已存在",113),
    ROLE_IS_BINDING("该角色已被用户绑定，无法删除",114),
    FILE_IS_NULL("上传文件为空",115),
    RETURN_IS_ERROR("返回参数错误",116),
    PARENT_IS_OVER("parent不可添加",117),
    PARENT_NOT_OVER("parent可添加",118),


    POST_ERROR("添加失败",194),
    PUT_ERROR("修改失败",195),
    DELETE_ERROR("删除失败",196),
    TRANSACTIONAL_IS_WORKED("操作失败（事务）",197),
    MISSING_SERVLET_REQUEST_PARAMETER("缺少Servlet请求参数",198),
    DUPLICATE_KEY("唯一值在数据库中重复",199),
    ERROR("操作失败",500);

    private final String message;
    private final int status;

    ResponseStatus(String message,int status){
        this.message=message;
        this.status=status;
    }
}

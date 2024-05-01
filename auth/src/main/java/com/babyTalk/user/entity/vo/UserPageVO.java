package com.babyTalk.user.entity.vo;

import lombok.Data;

@Data
public class UserPageVO {
    private Integer current;
    private Integer size;
    private String username;
    private Integer role;
    private String nickname;
}

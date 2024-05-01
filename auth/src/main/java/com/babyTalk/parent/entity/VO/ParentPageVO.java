package com.babyTalk.parent.entity.VO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class ParentPageVO {

    private Integer current;
    private Integer size;
    private String name;


}

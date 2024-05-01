package com.babyTalk.core.cryTranslateWords.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.sql.Timestamp;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 
 * </p>
 *
 * @author cst
 * @since 2021-10-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CryTranslateWords implements Serializable {

    public interface insert{}
    public interface update{}

    private static final long serialVersionUID=1L;

    /**
     * 哭声对应id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @NotNull(message = "id不能为空",groups = {update.class})
    private Integer id;

    /**
     * 哭声接口返回结果
     */
    private String cryWord;

    /**
     * 返回结果翻译
     */
    private String cryChinese;

    /**
     * 内容文案
     */
    private String content;

    /**
     * 建议
     */
    private String cryMsg;

    /**
     * 提示
     */
    private String cryTip;

    /**
     * 类型（1哭声识别、2面部识别
     */
    private String type;

    /**
     * 修改时间
     */
    private Timestamp updatedAt;

    /**
     * 创建时间
     */
    private Timestamp createdAt;


}

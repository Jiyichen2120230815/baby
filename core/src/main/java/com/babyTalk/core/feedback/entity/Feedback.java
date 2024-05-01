package com.babyTalk.core.feedback.entity;

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
 * @since 2021-10-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Feedback implements Serializable {

    public interface insert{}
    public interface update{}

    private static final long serialVersionUID=1L;

    /**
     * 反馈id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @NotNull(message = "id不为空",groups = update.class)
    private Integer id;

    /**
     * 翻译内容id
     */
    private Integer cryTranslateWordsId;

    /**
     * 建议
     */
    private String message;

    /**
     * 是否满意
     */
    private String isSatisfied;

    /**
     * 文件地址
     */
    private String fileUrl;

    /**
     * 创建时间
     */
    private Timestamp createdAt;

    /**
     * 修改时间
     */
    private Timestamp updatedAt;


}

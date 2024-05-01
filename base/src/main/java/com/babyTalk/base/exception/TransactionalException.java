package com.babyTalk.base.exception;

import com.babyTalk.base.http.ResponseStatus;
import lombok.Data;

@Data
public class TransactionalException extends RuntimeException{

    private Integer code;//状态码
    /**
     * 接受状态码和消息
     * @param code
     * @param message
     */
    public TransactionalException(Integer code, String message) {
        super(message);
        this.code=code;
    }
    /**
     * 接收枚举类型
     * @param responseStatus
     */
    public TransactionalException(ResponseStatus responseStatus) {
        super(responseStatus.getMessage());
        this.code = responseStatus.getStatus();
    }
}

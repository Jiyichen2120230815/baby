package com.babyTalk.base.http;

import lombok.Data;

/**
 * 返回接口类
 * @param <T>
 */
@Data
public class ResponseData<T> {
    private String message;
    private int status;
    private String token;
    private String refreshToken;
    private T data;

    public ResponseData(){
    }

    public ResponseData(String message,int status){
        this.message=message;
        this.status=status;
    }

    public ResponseData(String message,int status,T data){
        this.message=message;
        this.status=status;
        this.data=data;
    }

    public ResponseData(String message,int status,String token,String refreshToken){
        this.message=message;
        this.status=status;
        this.token=token;
        this.refreshToken=refreshToken;
    }

    public ResponseData(String message,int status,String token,String refreshToken,T data){
        this.message=message;
        this.status=status;
        this.token=token;
        this.refreshToken=refreshToken;
        this.data=data;
    }

    public void setMessageState(String message, int state) {
        this.message = message;
        this.status = state;
    }


    public void setTokens(String token, String refreshToken) {
        this.token = token;
        this.refreshToken = refreshToken;
    }
}

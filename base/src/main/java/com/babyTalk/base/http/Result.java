package com.babyTalk.base.http;

public class Result {
    public Result(){
    }
    //默认成功返回
    public static <T> ResponseData <T> success(){
        return new ResponseData<>(ResponseStatus.SUCCESS.getMessage(),ResponseStatus.SUCCESS.getStatus());
    }
    //自定义成功消息
    public static <T> ResponseData <T> success(String message){
        return new ResponseData<>(message,ResponseStatus.SUCCESS.getStatus());
    }
    //自定义返回成功信息带数据
    public static <T> ResponseData <T> success(String message,T data){
        return new ResponseData<>(message,ResponseStatus.SUCCESS.getStatus(),data);
    }
    //成功只返回数据
    public static <T> ResponseData <T> success(T data){
        return new ResponseData<>(ResponseStatus.SUCCESS.getMessage(), ResponseStatus.SUCCESS.getStatus(),data);
    }
    //成功带数据、token、refreshToken
    public static <T> ResponseData <T> success(String token,String refreshToken,T data){
        return new ResponseData<>(ResponseStatus.SUCCESS.getMessage(),ResponseStatus.SUCCESS.getStatus(),token,refreshToken,data);
    }
    //默认错误返回
    public static <T> ResponseData <T> error(){
        return new ResponseData<>(ResponseStatus.ERROR.getMessage(),ResponseStatus.ERROR.getStatus());
    }
    //自定义错误信息返回
    public static ResponseData<Object> error(String message) {
        return new ResponseData<>(message, ResponseStatus.ERROR.getStatus());
    }
    //自定义错误信息和状态码返回
    public static ResponseData<Object> error(String message, Integer status) {
        return new ResponseData<>(message, status);
    }
    //自定义错误信息状态码和数据返回
    public static ResponseData<Object> error(String message, Integer status, Object data) {//这里没用T
        return new ResponseData<>(message, status, data);
    }
}

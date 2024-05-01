package com.babyTalk.main.globalException;

import com.babyTalk.base.exception.TransactionalException;
import com.babyTalk.base.http.ResponseData;
import com.babyTalk.base.http.ResponseStatus;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@ResponseBody
@ControllerAdvice
public class GlobalExceptionController {
    @Autowired
    private Logger logger;
    /**
     * 处理请求参数格式错误的返回信息，json格式时
     *
     * @param e：MethodArgumentNotValidException异常
     * @return 102
     */
    @ExceptionHandler
    public ResponseData<Object> MethodArgumentNotValidException(MethodArgumentNotValidException e) {
//        System.out.println(e);
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        String message = null;
        for (ObjectError error : errors) {
            if (message != null) {
                message = message + ";" + error.getDefaultMessage();
            } else {
                message = error.getDefaultMessage();
            }
        }
        logger.error(message);
        return new ResponseData<>(message, ResponseStatus.PARAM_IS_ERROR.getStatus());
    }
    /**
     * 处理请求参数格式错误的返回信息,form-data格式时
     *
     * @param e：BindException异常
     * @return 102
     */
    @ExceptionHandler
    public ResponseData<Object> BindExceptionHandler(BindException e) {
        System.out.println(e);
        BindingResult bindingResult = e.getBindingResult();
        String message = bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining());
        return new ResponseData<>(message, ResponseStatus.PARAM_IS_ERROR.getStatus());
    }


    /**
     * 处理请求参数格式错误的返回信息，param格式时
     *
     * @param e：MethodArgumentNotValidException异常
     * @return 102
     */
    @ExceptionHandler
    public ResponseData<Object> constraintViolationException(ConstraintViolationException e) {
        String message = e.getMessage();
        System.out.println(message);
        return new ResponseData<>(message, ResponseStatus.PARAM_IS_ERROR.getStatus());
    }

    /**
     * 请求参数错误
     *
     * @param e：请求参数错误
     * @return 102
     */
    @ExceptionHandler
    public ResponseData<Object> HttpMessageNotReadableException(HttpMessageNotReadableException e) {
        logger.error(ResponseStatus.PARAM_IS_ERROR.getMessage());
        logger.error(e.getLocalizedMessage());
        return new ResponseData<>(ResponseStatus.PARAM_IS_ERROR.getMessage(), ResponseStatus.PARAM_IS_ERROR.getStatus());
    }
    /**
     * 唯一值在数据库中重复
     * Duplicate entry ' ' for key ' '
     * @param e：重复键异常
     * @return 199
     */
    @ExceptionHandler
    public ResponseData<Object> DuplicateKeyException(DuplicateKeyException e) {
        logger.error(ResponseStatus.DUPLICATE_KEY.getMessage());
        logger.error(e.getLocalizedMessage());
        System.err.println(e.getCause().getMessage());
        final String message = e.getCause().getMessage();
        final String[] split = message.split("'");//分割出错误信息，错误信息对应数据库中索引的name
        System.out.println("11111111111111"+ Arrays.toString(split));
        return new ResponseData<>(ResponseStatus.DUPLICATE_KEY.getMessage(), ResponseStatus.DUPLICATE_KEY.getStatus(),split[3]);
    }

    /**
     *  删除时缺少id
     *
     * @param e：缺少Servlet请求参数异常
     * @return 199
     */
    @ExceptionHandler
    public ResponseData<Object> MissingServletRequestParameterException(MissingServletRequestParameterException e) {
        logger.error(ResponseStatus.MISSING_SERVLET_REQUEST_PARAMETER.getMessage());
        logger.error(e.getLocalizedMessage());
        System.err.println(e.getMessage());
        return new ResponseData<>(ResponseStatus.MISSING_SERVLET_REQUEST_PARAMETER.getMessage(), ResponseStatus.MISSING_SERVLET_REQUEST_PARAMETER.getStatus(),e.getMessage());
    }

    /**
     *  删除时缺少id
     *
     * @param e：缺少Servlet请求参数异常
     * @return 199
     */
    @ExceptionHandler
    public ResponseData<Object> TransactionalException(TransactionalException e) {
        System.out.println(e.toString());//TransactionalException(code=197)
        System.out.println(e.getCode());//197
        System.out.println(e.getLocalizedMessage());//操作失败（事务）
        System.err.println(e.getMessage());//操作失败（事务）
        logger.error(ResponseStatus.TRANSACTIONAL_IS_WORKED.getMessage());//2021-08-22 13:06:49.839 ERROR 43560 --- [nio-8082-exec-2] rConfig$$EnhancerBySpringCGLIB$$9591bd41 : 操作失败（事务）
        logger.error(e.getLocalizedMessage());//2021-08-22 13:06:49.839 ERROR 43560 --- [nio-8082-exec-2] rConfig$$EnhancerBySpringCGLIB$$9591bd41 : 操作失败（事务）
        return new ResponseData<>(e.getMessage(),e.getCode());
    }
}

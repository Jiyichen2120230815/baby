package com.babyTalk.user.filter;

import com.alibaba.fastjson.JSON;
import com.babyTalk.base.http.ResponseData;
import com.babyTalk.base.http.ResponseStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义授权异常处理类
 * SimpleAccessDeniedHandler
 *
 * @author
 * @version 1.0
 * @date 2021/6/18
 */
@Component
public class SimpleAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ResponseData<Object> responseData = new ResponseData<>(ResponseStatus.WITHOUT_PERMISSION.getMessage(), ResponseStatus.WITHOUT_PERMISSION.getStatus());
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().append(JSON.toJSONString(responseData));
    }
}

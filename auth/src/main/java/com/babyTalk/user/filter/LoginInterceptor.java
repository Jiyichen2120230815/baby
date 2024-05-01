package com.babyTalk.user.filter;


import com.alibaba.fastjson.JSON;
import com.babyTalk.base.annotation.UnLogin;
import com.babyTalk.base.http.ResponseData;
import com.babyTalk.base.http.ResponseStatus;
import com.babyTalk.user.utils.JwtUtils;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private Logger logger;
    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setCharacterEncoding("utf8");
        response.setContentType("text/json;charset = UTF-8");
        ResponseData<Object> responseData = new ResponseData<>();
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        //检查是否有LoginToken注解，有则跳过认证
        if (method.isAnnotationPresent(UnLogin.class)) {
            UnLogin unLogin = method.getAnnotation(UnLogin.class);
            if (unLogin.value()) {
                return true;
            }
        }
        //如果是通过扫码登录，跳过认证
        String authorization = request.getHeader("Authorization");
        if (authorization == null) {
            responseData.setMessageState(ResponseStatus.TOKEN_NOT_PROVIDE.getMessage(), ResponseStatus.TOKEN_NOT_PROVIDE.getStatus());
            String result = JSON.toJSONString(responseData);
            response.getWriter().append(result);
            return false;
        }
        String[] bearer = authorization.split(" ");
        if (!bearer[0].equals("Bearer")) {
            responseData.setMessageState(ResponseStatus.TOKEN_IS_ERROR.getMessage(), ResponseStatus.TOKEN_IS_ERROR.getStatus());
            String result = JSON.toJSONString(responseData);
            response.getWriter().append(result);
            return false;
        }
        String token = bearer[1];
        //检查token是否有效
        try {
            System.out.println("检查token是否有效");
            UserDetails userDetails = jwtUtils.getUserDetails(token, request);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(
                    request));
            //设置用户登录状态
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return true;
        } catch (NoClassDefFoundError e) {
            logger.warn("token错误");
            logger.error(e.getMessage());
            responseData.setMessageState(ResponseStatus.TOKEN_IS_ERROR.getMessage(), ResponseStatus.TOKEN_IS_ERROR.getStatus());//101
            String Json = JSON.toJSONString(responseData);
            response.getWriter().append(Json);
            return false;
        } catch (ExpiredJwtException e) {
            logger.warn("token已过期");
            logger.error(e.getMessage());
            responseData.setMessageState("token已过期", ResponseStatus.TOKEN_IS_EXPIRED.getStatus());
            String Json = JSON.toJSONString(responseData);
            response.getWriter().append(Json);
            return false;
        } catch (Exception e) {
            logger.error("发生错误");
            e.getStackTrace();
            responseData.setMessageState(ResponseStatus.TOKEN_IS_ERROR.getMessage(), ResponseStatus.TOKEN_IS_ERROR.getStatus());
            String Json = JSON.toJSONString(responseData);
            response.getWriter().append(Json);
            return false;
        }

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}

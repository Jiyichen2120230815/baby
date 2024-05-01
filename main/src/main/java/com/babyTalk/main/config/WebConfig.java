package com.babyTalk.main.config;

//import com.storage.auth.storageUser.filter.CORSInterceptor;
//import com.operative.auth.user.filter.CORSInterceptor;
//import com.operative.auth.user.filter.LoginInterceptor;

import com.babyTalk.user.filter.CORSInterceptor;
import com.babyTalk.user.filter.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private CORSInterceptor corsInterceptor;
    @Autowired
    private LoginInterceptor loginInterceptor;

    @Value("${path.static}")
    private String staticPath;

    String[] loginPaths = new String[]{
            "/**"
    };
    String[] unLoginPaths = new String[]{
            "/error",
            "/static/**",
            "/webjars/**",
            "/swagger-resources/**",
            "/v2/api-docs",
            "/swagger-ui.html"
    };

    //相对路径访问静态资源，如果使用docker部署的环境注意添加目录挂载
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/"+staticPath+"/**").addResourceLocations("file:"+staticPath+System.getProperty("file.separator"));
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(corsInterceptor).addPathPatterns("/**");
//        System.out.println(123123123);
        registry.addInterceptor(loginInterceptor).addPathPatterns(loginPaths).excludePathPatterns(unLoginPaths);
    }



}

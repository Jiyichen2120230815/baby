package com.babyTalk.main;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@MapperScan("com.babyTalk.**.mapper")
@ComponentScan(basePackages = {"com.babyTalk.core","com.babyTalk","com.babyTalk.main"})
//@EnableTransactionManagement//开启mysql事务
@SpringBootApplication
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class,args);
    }
}

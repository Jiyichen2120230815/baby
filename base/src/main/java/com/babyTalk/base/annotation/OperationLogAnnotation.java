package com.babyTalk.base.annotation;

import java.lang.annotation.*;

/**
 * @author cst
 * @title: OperationLog
 * @projectName springcloud
 * @date 2021/7/10
 * @description: 自定义操作日志注解
 */
@Target(ElementType.METHOD)//注解放置的目标位置即方法级别
@Retention(RetentionPolicy.RUNTIME)//注解在哪个阶段执行
@Documented
public @interface OperationLogAnnotation {

    String type() default "";//操作

    String content() default "";//事件

//    String operModul() default ""; // 操作模块
//
//    String operType() default "";  // 操作类型
//
//    String operDesc() default "";  // 操作说明
}


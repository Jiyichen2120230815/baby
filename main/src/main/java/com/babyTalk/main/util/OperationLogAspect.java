package com.babyTalk.main.util;//package com.management.main.util;
//
//import com.alibaba.fastjson.JSON;
//import com.management.base.annotation.OperationLogAnnotation;
//import com.management.base.http.ResponseData;
//import com.management.core.sysLog.entity.SysLog;
//import com.management.core.sysLog.mapper.SysLogMapper;
//import com.management.user.entity.User;
//import com.management.user.mapper.UserMapper;
//import com.management.user.utils.JwtUtils;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.AfterReturning;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestAttributes;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//import java.lang.reflect.Method;
//
///**
// * @author lyz
// * @title: OperationAspect
// * @projectName springcloud
// * @date 2020/9/23
// * @description: 操作日志切面处理类
// */
//@Aspect
//@Component
//
//public class OperationLogAspect {
//    @Autowired
//    private SysLogMapper sysLogMapper;
//    @Autowired
//    private UserMapper userMapper;
//    @Autowired
//    private JwtUtils jwtUtils;
//
//    private String ip;
//    private String token;
//
//    /**
//     * 设置操作日志切入点   在注解的位置切入代码
//     */
//    @Pointcut("@annotation(com.operative.base.annotation.OperationLogAnnotation)")
//    public void operLogPoinCut() {
//    }
//
//
//    @Before("operLogPoinCut()")
//    public void beforeAop(JoinPoint joinPoint){
//        try {
//            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
//            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
//            final String ip = request.getRemoteAddr();//用户客户端的ip地址
//
//            this.ip = ip;
//
//            final String authorization = request.getHeader("Authorization");
//            if (authorization!=null){
//                final String token = authorization.split(" ")[1];
//                this.token = token;
//            }
//            System.out.println("ip" + ip);
//            System.out.println("token" + token);
//            System.out.println("请求参数" + JSON.toJSONString(joinPoint.getArgs()));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//
//
//    /**
//     * 记录操作日志
//     * @param joinPoint 方法的执行点
//     * @param result  方法返回值
//     * @throws Throwable
//     */
//    @AfterReturning(returning   = "result", value = "operLogPoinCut()")
//    public void saveOperLog(JoinPoint joinPoint, Object result) throws Throwable {
//        try {
//
//            //将返回值转换成map集合
//            ResponseData<Object> map =  (ResponseData<Object>) result;
//            SysLog operationLog = new SysLog();
//            // 从切面织入点处通过反射机制获取织入点处的方法
//            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//            //获取切入点所在的方法
//            Method method = signature.getMethod();
//            //获取操作
//            OperationLogAnnotation annotation = method.getAnnotation(OperationLogAnnotation.class);
//            if (!annotation.type().equals("")) {
//                operationLog.setType(annotation.type());//内容
//            }
//            //          返回值信息
//
//            if (!annotation.content().equals("")) {
//                operationLog.setContent(annotation.content());//返回信息或者注解内容
//            }else {
//                operationLog.setContent(map.getMessage());//返回信息或者或者注解内容
//            }
////            if (map.getMessage()!=null){
////                operationLog.setType(map.getMessage());
////            }
//
//            //获取操作员
//            if (token!=null){
//                final User loginUser = jwtUtils.getLoginUser(token);
//                operationLog.setUserId(loginUser.getId());
//                operationLog.setIp(ip);
//                this.ip = null;
//                this.token = null;
//            }else if ((User) map.getData()!=null&&((User) map.getData()).getId()!=null){
//                operationLog.setUserId(((User) map.getData()).getId());
//                operationLog.setIp(ip);
//                this.ip = null;
//                this.token = null;
//            }else {
//                System.out.println("没有token的其他情况");
////                operationLog.setUserId();
//                operationLog.setIp(ip);
//                this.ip = null;
//                this.token = null;
//            }
//
//            //保存日志
//            sysLogMapper.insert(operationLog);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//}
//

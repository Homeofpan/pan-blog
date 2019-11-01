package com.pan.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Objects;


/**
 * 记录日志内容
 *  请求url
 *  访问者ip
 *  调用方法classMethod
 *  参数args
 *  返回内容
 */
@Aspect
@Component
public class LogAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    ThreadLocal<Long> startTime = new ThreadLocal<>();

    //对controller下所有类所有方法进行切入
    @Pointcut("execution(* com.pan.controller.*.*(..))")
    public void log() {
    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        //记录请求的开始的时间
        startTime.set(System.currentTimeMillis());

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //获取请求的url
        String url = request.getRequestURL().toString();
        //获取请求的ip地址
        String ip = request.getRemoteAddr();
        //获取请求的Controller方法
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        //获取请求的参数
        Object[] args = joinPoint.getArgs();
        RequestLog requestLog = new RequestLog(url,ip,classMethod,args);
        logger.info("Request : {}",requestLog);
    }

    @After("log()")
    public void doAfter() {
//        logger.info("----------doAfter-----------");
    }

    //目标方法成功执行完成后,输出结果
    @AfterReturning(returning = "result",pointcut = "log()")
    public void doAfterReturn(Object result){
        logger.info("Result : {}" , result);
        Long start = startTime.get();
        Long time = System.currentTimeMillis()-start;
        logger.info("Spend time: {}",time);
        //一定要remove,不然会造成内存泄漏
        startTime.remove();
    }

    /**
     * 一个内部类来封装请求方的一些信息,方便打印信息
     * */
    private class RequestLog{
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;

        public RequestLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }
}

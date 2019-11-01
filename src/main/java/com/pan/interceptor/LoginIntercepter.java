package com.pan.interceptor;

import com.pan.annotation.AccessLimit;
import com.pan.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *这里留着一个未实现的功能,用redis记录用户的访问该接口的次数
 *
* */
public class LoginIntercepter extends HandlerInterceptorAdapter{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static Integer count = 0;
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        if(handler instanceof HandlerMethod){
            HandlerMethod hm = (HandlerMethod) handler;

            //获取方法中的注解,看是否有该注解
            AccessLimit accessLimit = hm.getMethodAnnotation(AccessLimit.class);
            if(accessLimit == null) {
                if(!isLogined(request)){
                    logger.info("用户没登录或者登录过期,请重新登录");
                    response.sendRedirect("/admin/login");
                    return false;
                }
                return true;
            }
            int seconds = accessLimit.seconds();
            int maxCount = accessLimit.maxCount();
            boolean login = accessLimit.needLogin();
            String key = request.getRequestURI();


            if(count == 0){
                count++;
                logger.info("用户没登录或者登录过期,请重新登录");
                response.sendRedirect("/admin/login");
                return false;
            }else if(count < maxCount){
                count++;
                logger.info("用户没登录或者登录过期,请重新登录");
                response.sendRedirect("/admin/login");
                return false;
            }else {
                logger.info("用户存在恶意访问接口,请立刻停止该行为");
                response.sendRedirect("/error/fangshua");
                return false;
            }

        }
        return true;
    }

    private boolean isLogined(HttpServletRequest request){
        if(request.getSession().getAttribute("user") == null){
            return false;
        }
        return true;
    }
}


package com.pan.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

//拦截所有标注了@Controller的控制器
@ControllerAdvice
public class ExceptionInterceptor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //标注是用来做异常处理的,Exception.class表示拦截级别
    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionInterceptor(HttpServletRequest request, Exception e) throws Exception {
        logger.error("Request URL : {} Exception : {}",request.getRequestURL(),e);

        //判断连接到的错误是否标注有ResonseStatus注解
        if(AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null){
            throw e;
        }

        ModelAndView mv = new ModelAndView();
        mv.addObject("url",request.getRequestURL());
        mv.addObject("exception",e);
        mv.setViewName("error/error");
        return mv;
    }
}

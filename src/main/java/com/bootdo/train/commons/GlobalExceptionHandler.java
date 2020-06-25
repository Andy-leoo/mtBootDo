package com.bootdo.train.commons;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {


    // @ExceptionHandler用来定义函数针对的异常类型，最后将Exception对象和请求URL映射到error.html中
    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest request, Exception e) throws Exception {
        ModelAndView mav = new ModelAndView() ;
        mav.addObject("errorname", "统一异常处理页面") ;
        mav.addObject("exception", e) ;
        mav.addObject("url", request.getRequestURL()) ;
        mav.setViewName(Const.DEFAULT_ERROR_VIEW) ;
        return mav ;
    }
}

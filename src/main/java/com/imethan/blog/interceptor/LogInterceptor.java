package com.imethan.blog.interceptor;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Log4j2
public class LogInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
//        log.info("Request Method：{},URI：{}，QueryString：{}", request.getMethod(), request.getRequestURI(), request.getQueryString());
        return true;
    }
}

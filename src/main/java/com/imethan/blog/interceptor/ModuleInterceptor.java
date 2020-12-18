package com.imethan.blog.interceptor;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Name ModuleInterceptor
 * @Description ${DESCRIPTION}
 * @Author huangyingfeng
 * @Create 2019-10-30 19:51
 */
@Component
@Log4j2
public class ModuleInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String module = "";
        String uri = request.getRequestURI();

        String[] uris = uri.split("/");
//        log.info("uris={}", uris);
        if (uris.length > 0) {
            module = uris[1];
        }

        request.setAttribute("module", module);


//        log.info("uri={},module={}", uri, module);

        return true;
    }

}

package com.imethan.blog.configuration.security;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Name LoginSuccessHandler
 * @Description ${DESCRIPTION}
 * @Author huangyingfeng
 * @Create 2018-09-27 20:25
 */
@Log4j2
public class LoginSuccessHandler extends
        SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        //检查验证码是否正确
        if (request.getMethod().equals("POST") && request.getRequestURI().equals("/signin")) {
            String code = request.getParameter("code");
            String validCode = (String) request.getSession().getAttribute("valid_code");
            log.info("SecurityFilter doFilter valid code={},request code={}", validCode, code);
            if (!code.equals(validCode)) {
                throw new AuthenticationServiceException("校验码不正确！");
            }
        }

        User user = (User) authentication.getPrincipal();

        log.info("user {} login,ip is {}", user, getIpAddress(request));
        super.onAuthenticationSuccess(request, response, authentication);
    }

    public String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}

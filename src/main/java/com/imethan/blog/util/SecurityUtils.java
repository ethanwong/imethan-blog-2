package com.imethan.blog.util;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @Name SecurityUtils
 * @Description
 * @Author huangyingfeng
 * @Create 2020-12-30 11:30
 */
public class SecurityUtils {

    /**
     * 是否已经登录
     *
     * @return
     */
    public static boolean isLogin() {
        if (SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken) {
            return false;
        } else {
            return true;
        }
    }
}

package com.imethan.blog.interceptor;

import com.imethan.blog.common.Constant;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
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
        if (uris.length > 0) {
            module = uris[1];
        }

        request.setAttribute("module", module);
        if (StringUtils.isBlank(module)) {
            module = "home";
        }

        request.setAttribute("SITE_TITLE", captureName(module) + " - " + Constant.SITE_TITLE);
        request.setAttribute("TITLE", Constant.SITE_TITLE);

        return true;
    }

    /**
     * 将字符串的首字母转大写
     *
     * @param str 需要转换的字符串
     * @return
     */
    private String captureName(String str) {
        // 进行字母的ascii编码前移，效率要高于截取字符串进行转换的操作
        char[] cs = str.toCharArray();
        cs[0] -= 32;
        return String.valueOf(cs);
    }

}

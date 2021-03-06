package com.imethan.blog.configuration.security;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @Name CustomInvocationSecurityMetadataSourceService
 * @Description ${DESCRIPTION}
 * @Author huangyingfeng
 * @Create 2018-09-28 10:43
 */
@Component
public class InvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private static Map<String, Collection<ConfigAttribute>> resourceMap = null;

    @PostConstruct
    private void loadResourceDefine() {
        if (resourceMap == null) {
            resourceMap = new HashMap<>();
            Collection<ConfigAttribute> configAttributes = new ArrayList<>();
            ConfigAttribute configAttribute = new SecurityConfig("HOMEGET");
            configAttributes.add(configAttribute);
            resourceMap.put("/console#GET", configAttributes);

            Collection<ConfigAttribute> configAttributes2 = new ArrayList<>();
            ConfigAttribute configAttribute2 = new SecurityConfig("INDEXGET");
            configAttributes2.add(configAttribute2);
//            resourceMap.put("/index#GET", configAttributes2);
        }

    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        FilterInvocation filterInvocation = (FilterInvocation) object;
        if (resourceMap == null) {
            loadResourceDefine();
        }
        Iterator<String> ite = resourceMap.keySet().iterator();
        while (ite.hasNext()) {
            RequestMatcher requestMatcher = null;
            String resURL = ite.next();
            try {
                String[] urls = StringUtils.split(resURL, "#");
                requestMatcher = new AntPathRequestMatcher(urls[0], urls[1]);
            } catch (Exception e) {
                requestMatcher = new AntPathRequestMatcher(resURL);
            }

            if (requestMatcher.matches(filterInvocation.getHttpRequest())) {
                return resourceMap.get(resURL);
            }
        }
        return null;

    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return new ArrayList<>();
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}

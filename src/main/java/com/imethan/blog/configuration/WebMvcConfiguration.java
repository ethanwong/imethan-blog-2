package com.imethan.blog.configuration;


import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.imethan.blog.interceptor.LogInterceptor;
import com.imethan.blog.interceptor.ModuleInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfiguration {

    /**
     * 日志拦截器
     */
    @Autowired
    private LogInterceptor logInterceptor;

    @Autowired
    private ModuleInterceptor moduleInterceptor;


    /**
     * 实例化WebMvcConfigurer接口
     *
     * @return
     */
    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            /**
             * 添加拦截器
             * @param registry
             */
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(logInterceptor).addPathPatterns("/**");
                registry.addInterceptor(moduleInterceptor).addPathPatterns("/**")
                        .excludePathPatterns("/webjars/**")
                        .excludePathPatterns("/static/**")
                        .excludePathPatterns("/decorator/**")
                ;

            }

            @Override
            public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
                FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
                FastJsonConfig config = new FastJsonConfig();
                config.setDateFormat("yyyy-MM-dd HH:MM:SS");
                converter.setFastJsonConfig(config);
                converters.add(converter);
            }

        };


    }
}
package com.imethan.blog.controller.sitemesh;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Name WebConfig
 * @Description ${DESCRIPTION}
 * @Author huangyingfeng
 * @Create 2019-10-29 14:05
 */
@Configuration
public class WebConfiguration {

    @Bean(name = "sitemesh3Filter")
    Sitemesh3Filter sitemesh3Filter() {
        return new Sitemesh3Filter();
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean(@Qualifier("sitemesh3Filter") Sitemesh3Filter siteMeshFilter) {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(siteMeshFilter);
        filterRegistrationBean.setEnabled(true);
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }
}

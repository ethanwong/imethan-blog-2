package com.imethan.blog.configuration;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Name ShutdownConfiguration
 * @Description
 * @Author huangyingfeng
 * @Create 2021-01-13 11:40
 */
@Configuration
@Log4j2
public class ShutdownConfiguration {

//    @Autowired
//    private ThreadPoolTaskExecutor blogTaskExecutor;


    @Bean
    public GracefulShutdown gracefulShutdown() {
        return new GracefulShutdown();
    }

    @Bean
    public ServletWebServerFactory servletContainer() {
        log.info("init TomcatServletWebServerFactory");
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        tomcat.addConnectorCustomizers(gracefulShutdown());
        return tomcat;
    }


}

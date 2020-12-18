package com.imethan.blog.daemon.init;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @Name InitDemo
 * @Description
 * @Author huangyingfeng
 * @Create 2020-12-18 11:14
 */
@Component
@Order(value = 1)
@Log4j2
public class InitDemo implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}

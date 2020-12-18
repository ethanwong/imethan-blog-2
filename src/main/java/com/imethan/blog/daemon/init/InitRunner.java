package com.imethan.blog.daemon.init;

import com.imethan.blog.service.AccountService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
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
public class InitRunner implements ApplicationRunner {

    @Autowired
    AccountService accountService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("InitRunner run");
        accountService.checkRootUser();
    }
}

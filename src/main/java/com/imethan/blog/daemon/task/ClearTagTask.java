package com.imethan.blog.daemon.task;

import com.imethan.blog.service.TagService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Name ClearTagTask
 * @Description
 * @Author huangyingfeng
 * @Create 2020-12-18 10:36
 */
@Component
@Log4j2
public class ClearTagTask {

    @Autowired
    private TagService tagService;

//    @Scheduled(cron = "0 0 23 * * ?")
    @Scheduled(fixedDelay = 300000,initialDelay = 10000)
    public void execute(){
        log.warn("ClearTagTask execute");
        tagService.clearTag();

    }
}

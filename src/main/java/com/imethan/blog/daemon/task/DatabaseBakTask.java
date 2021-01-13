package com.imethan.blog.daemon.task;

import com.imethan.blog.manage.MongodbExportManage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Name DatabaseBakTask
 * @Description
 * @Author huangyingfeng
 * @Create 2021-01-13 10:34
 */
@Component
@Log4j2
public class DatabaseBakTask {

    @Autowired
    private MongodbExportManage mongodbExportManage;


    @Scheduled(cron = "0 0 23 * * ?")
    public void execute(){
        log.info("DatabaseBakTask execute");
        mongodbExportManage.export();

    }
}

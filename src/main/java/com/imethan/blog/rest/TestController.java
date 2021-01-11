package com.imethan.blog.rest;

import com.imethan.blog.dto.ResultDto;
import com.imethan.blog.util.MongoExportUtils;
import com.mongodb.client.MongoCursor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Name TestController
 * @Description
 * @Author huangyingfeng
 * @Create 2021-01-11 15:39
 */
@RestController
@Log4j2
@RequestMapping(value = "/api/test")
public class TestController {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 动态设置日志的级别
     *
     * @return
     */
    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping("set")
    @ResponseBody
    public ResultDto setLevel() {

        Collection<Logger> current = LoggerContext.getContext(false).getLoggers();
        Collection<Logger> notcurrent = LoggerContext.getContext().getLoggers();
        Collection<Logger> allConfig = current;
        allConfig.addAll(notcurrent);
        for (org.apache.logging.log4j.core.Logger log : allConfig) {
            log.setLevel(Level.ERROR);
        }

        return ResultDto.ReturnSuccess();
    }

    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping("log")
    @ResponseBody
    public ResultDto log() {
        log.info("test info log level = {}", log.getLevel());
        log.error("test error log level = {}", log.getLevel());
        return ResultDto.ReturnSuccess();
    }

    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping("mongodb/export")
    @ResponseBody
    public ResultDto dump() {
        String database = mongoTemplate.getDb().getName();
        log.info("database name:{}", database);
        List<String> collections = new ArrayList<>();
        MongoCursor<Document> iterator = mongoTemplate.getDb().listCollections().iterator();
        while (iterator.hasNext()) {
            Document document = iterator.next();
            String collectionName = document.getString("name");

            collections.add(collectionName);
        }
        log.info("collections:{}", collections);
        MongoExportUtils.exportAll(database, collections);

        return ResultDto.ReturnSuccess();
    }

}

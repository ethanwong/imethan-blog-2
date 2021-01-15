package com.imethan.blog.rest;

import com.imethan.blog.dto.ResultDto;
import com.imethan.blog.manage.MongodbExportManage;
import com.imethan.blog.service.EmailService;
import com.imethan.blog.util.MongoExportUtils;
import com.imethan.blog.util.TimeUtils;
import com.mongodb.client.MongoCursor;
import lombok.extern.log4j.Log4j2;
import okhttp3.*;
import okhttp3.RequestBody;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @Name CommonRestApi
 * @Description
 * @Author huangyingfeng
 * @Create 2021-01-11 15:39
 */
@Controller
@Log4j2
@RequestMapping(value = "/api/common")
public class CommonRestApi {

    @Autowired
    private MongodbExportManage mongodbExportManage;

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

    /**
     * 查看日志打印级别
     *
     * @return
     */
    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping("log")
    @ResponseBody
    public ResultDto log() {
        log.info("test info log level = {}", log.getLevel());
        log.error("test error log level = {}", log.getLevel());
        return ResultDto.ReturnSuccess();
    }

    /**
     * 手动触发导出mongodb数据
     *
     * @param request
     * @return
     */
    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping("mongodb/export")
    @ResponseBody
    public ResultDto dump(HttpServletRequest request) {

        mongodbExportManage.export();

        return ResultDto.ReturnSuccess();
    }


}
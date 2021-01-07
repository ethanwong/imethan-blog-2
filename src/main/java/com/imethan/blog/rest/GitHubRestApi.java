package com.imethan.blog.rest;

import com.imethan.blog.service.AutoDeployService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @Name GitHubRestApi
 * @Description
 * @Author huangyingfeng
 * @Create 2021-01-07 10:30
 */
@RestController
@Log4j2
@RequestMapping(value = "/api/github")
public class GitHubRestApi {

    @Autowired
    AutoDeployService autoDeployService;

    /**
     * Secret imethan1234567890
     *
     * @return
     */
    @PostMapping("webhook")
    @ResponseBody
    public String webhook(@RequestBody String body) {
        log.info("GitHubRestApi body = {}", body);
        autoDeployService.execShell();
        return "ok";
    }

    @ResponseBody
    @PreAuthorize(value = "permitAll()")
    @GetMapping("test")
    public String test(){
        return "ok";
    }
}

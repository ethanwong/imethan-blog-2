package com.imethan.blog.web;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Name AdminController
 * @Description
 * @Author huangyingfeng
 * @Create 2020-12-29 16:13
 */
@Controller
@Log4j2
@RequestMapping(value = "admin")
public class AdminController {

    @GetMapping(value = "")
    public String console() {
        return "admin";
    }
}

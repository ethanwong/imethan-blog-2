package com.imethan.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Name ConsoleController
 * @Description ${DESCRIPTION}
 * @Author huangyingfeng
 * @Create 2019-10-31 16:05
 */
@Controller
@RequestMapping(value = "console")
public class ConsoleController {

    @GetMapping(value = "")
    public String console() {
        return "/console/console";
    }

    @GetMapping(value = "/publish")
    public String publish(){
        return "/console/publish";
    }
}

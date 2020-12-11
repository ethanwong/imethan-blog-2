package com.imethan.blog.web;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Name HomeController
 * @Description ${DESCRIPTION}
 * @Author huangyingfeng
 * @Create 2018-09-27 20:50
 */
@Controller
@Log4j2
public class HomeController {

    @GetMapping(value = "")
    public String index() {
        return "home";
    }

    @GetMapping(value = "/home")
    public String home() {
        return "home";
    }


}

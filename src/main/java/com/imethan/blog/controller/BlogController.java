package com.imethan.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Name BlogController
 * @Description ${DESCRIPTION}
 * @Author huangyingfeng
 * @Create 2019-10-29 16:36
 */
@Controller
public class BlogController {

    @GetMapping(value = {"/blog"})
    public String blog() {
        return "blog";
    }

}

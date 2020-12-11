package com.imethan.blog.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Name BlogController
 * @Description ${DESCRIPTION}
 * @Author huangyingfeng
 * @Create 2019-10-29 16:36
 */
@Controller
@RequestMapping(value = "blog")
public class BlogController {

    @GetMapping(value = {""})
    public String blog() {
        return "/blog/blog";
    }

    @GetMapping(value = {"/article/{id}"})
    public String article(@PathVariable String id) {

        return "/blog/blog-article-md";
    }

    //TODO
    @GetMapping(value = {"/channel/{id}"})
    public String channel(@PathVariable String id) {

        return "/blog/blog";
    }

}

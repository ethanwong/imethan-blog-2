package com.imethan.blog.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @Name GitalkController
 * @Description
 * @Author huangyingfeng
 * @Create 2020-12-31 15:47
 */
@Controller
@RequestMapping(value = "gitalk")
public class GitalkController {

    @GetMapping(value = "")
    public String gitalk(Model model, HttpServletRequest request) {

        return "gitalk";
    }
}

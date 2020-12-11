package com.imethan.blog.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @Name AboutController
 * @Description ${DESCRIPTION}
 * @Author huangyingfeng
 * @Create 2019-10-30 15:42
 */
@Controller
@RequestMapping(value = "about")
public class AboutController {

    @GetMapping(value = "")
    public String about(Model model, HttpServletRequest request) {

        return "about";
    }

}

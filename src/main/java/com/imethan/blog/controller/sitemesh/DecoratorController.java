package com.imethan.blog.controller.sitemesh;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @Name DecoratorController
 * @Description ${DESCRIPTION}
 * @Author huangyingfeng
 * @Create 2019-10-29 13:53
 */
@Controller
@RequestMapping("decorator")
public class DecoratorController {

    @GetMapping("default")
    public String defaultDecorator(HttpServletRequest request) {
        return "decorator/default";
    }
}

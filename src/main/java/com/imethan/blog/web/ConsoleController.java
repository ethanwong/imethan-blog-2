package com.imethan.blog.web;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Name ConsoleController
 * @Description ${DESCRIPTION}
 * @Author huangyingfeng
 * @Create 2019-10-31 16:05
 */
@Controller
@Log4j2
@RequestMapping(value = "console")
public class ConsoleController {

    @GetMapping(value = "")
    public String console() {
        return "/console/console";
    }

    @GetMapping(value = "/article/input")
    public String inputArticle(){
        return "/console/article-input";
    }

    @GetMapping(value = "/article/modify/{id}")
    public String modifyArticle(@PathVariable String id, Model model){
        log.info("modify article id={}",id);
        model.addAttribute("id",id);
        return "/console/article-modify";
    }

    @GetMapping(value = "/setting")
    public String setting(){
        return "/console/setting";
    }
}

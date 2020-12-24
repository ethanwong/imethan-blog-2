package com.imethan.blog.web;

import com.alibaba.fastjson.JSONObject;
import com.imethan.blog.dto.ResultDto;
import com.imethan.blog.service.AccountService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private AccountService accountService;

    @GetMapping(value = "")
    public String console() {
        return "console/console";
    }

    @GetMapping(value = "/article/input")
    public String inputArticle(){
        return "console/article-input";
    }

    @GetMapping(value = "/article/modify/{id}")
    public String modifyArticle(@PathVariable String id, Model model){
        log.info("modify article id={}",id);
        model.addAttribute("id",id);
        return "console/article-modify";
    }

    @GetMapping(value = "/setting")
    public String setting(){
        return "console/setting";
    }

    @GetMapping(value = "/setting/{type}")
    public String settingContent(@PathVariable String type, Model model){
        model.addAttribute("type",type);
        return "console/setting";
    }

    @PostMapping("reset/password")
    @ResponseBody
    public ResultDto resetPassword(@RequestBody JSONObject password){
        return accountService.resetRootPassword(password.getString("password"));
    }
}

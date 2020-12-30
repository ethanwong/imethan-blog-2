package com.imethan.blog.web;

import com.alibaba.fastjson.JSONObject;
import com.imethan.blog.document.Setting;
import com.imethan.blog.dto.ResultDto;
import com.imethan.blog.service.AccountService;
import com.imethan.blog.service.SettingService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
    @Autowired
    private SettingService settingService;

    @GetMapping(value = "")
    public String console() {
        return "console/console";
    }

    @GetMapping(value = "/article/input")
    public String inputArticle() {
        return "console/article-input";
    }

    @GetMapping(value = "/article/modify/{id}")
    public String modifyArticle(@PathVariable String id, Model model) {
        log.info("modify article id={}", id);
        model.addAttribute("id", id);
        return "console/article-modify";
    }

    @GetMapping(value = "/setting")
    public String setting() {
        return "console/setting";
    }

//    @GetMapping(value = "/setting/{type}")
//    public String settingContent(@PathVariable String type, Model model) {
//        model.addAttribute("type", type);
//        return "console/setting";
//    }

    /**
     * 修改密码
     *
     * @param password
     * @return
     */
    @PostMapping("setting/password")
    @ResponseBody
    public ResultDto resetPassword(@RequestBody JSONObject password) {
        return accountService.resetRootPassword(password.getString("password"));
    }

//    /**
//     * 配置About
//     *
//     * @param setting
//     * @return
//     */
//    @PostMapping("setting")
//    @ResponseBody
//    public ResultDto saveSetting(@RequestBody Setting setting) {
//        return settingService.saveOrUpdate(setting);
//    }

    /**
     * 获取配置信息
     * @param module
     * @return
     */
    @GetMapping("setting/{module}")
    @ResponseBody
    public ResultDto getModuleSetting(@PathVariable String module) {
        List<Setting> list = settingService.findByModule(module);
        return ResultDto.ReturnSuccessData(list);
    }

    /**
     * About设置
     * @param setting
     * @return
     */
    @PostMapping("reset/about")
    @ResponseBody
    public ResultDto resetAbout(@RequestBody JSONObject setting) {
        String aboutShow = setting.getString("aboutShow");
        String aboutContentId = setting.getString("aboutContentId");

        List<Setting> list = new ArrayList<>();
        Setting aboutShowSetting = new Setting();
        aboutShowSetting.setModule("about");
        aboutShowSetting.setName("aboutShow");
        aboutShowSetting.setContent(aboutShow);
        list.add(aboutShowSetting);


        Setting aboutContentIdSetting = new Setting();
        aboutContentIdSetting.setModule("about");
        aboutContentIdSetting.setName("aboutContentId");
        aboutContentIdSetting.setContent(aboutContentId);
        list.add(aboutContentIdSetting);

        return settingService.batchSaveOrUpdate(list);
    }
}

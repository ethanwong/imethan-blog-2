package com.imethan.blog.controller.rest;

import com.imethan.blog.pojo.document.Setting;
import com.imethan.blog.pojo.dto.ResultDto;
import com.imethan.blog.service.SettingService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @Name SettingRestApi
 * @Description
 * @Author huangyingfeng
 * @Create 2021-01-15 17:00
 */
@RestController
@Log4j2
@RequestMapping(value = "/api/setting")
public class SettingRestApi {

    @Autowired
    private SettingService settingService;

    /**
     * 更新或者添加配置
     *
     * @param request
     * @param settings
     * @return
     */
    @PreAuthorize(value = "isAuthenticated()")
    @PutMapping("upset")
    public ResultDto upset(HttpServletRequest request, @RequestBody List<Setting> settings) {
        log.info("settings={}", settings);
        return settingService.batchSaveOrUpdate(settings);
    }

    /**
     * 获取配置信息
     *
     * @param module,多个模块以英文逗号隔开
     * @return
     */
    @PreAuthorize(value = "isAuthenticated()")
    @GetMapping("{module}")
    @ResponseBody
    public ResultDto getModuleSetting(@PathVariable String module) {
        String[] modules = module.split(",");
        List<Setting> list = new ArrayList<>();
        for (String temp : modules) {
            list.addAll(settingService.findByModule(temp));
        }
        return ResultDto.ReturnSuccessData(list);
    }
}

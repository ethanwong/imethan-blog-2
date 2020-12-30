package com.imethan.blog.rest;

import com.imethan.blog.document.Setting;
import com.imethan.blog.document.blog.Article;
import com.imethan.blog.dto.ResultDto;
import com.imethan.blog.service.ArticleService;
import com.imethan.blog.service.SettingService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Name AboutRestApi
 * @Description
 * @Author huangyingfeng
 * @Create 2020-12-30 17:17
 */
@RestController
@Log4j2
@RequestMapping(value = "/api/about")
public class AboutRestApi {

    @Autowired
    private SettingService settingService;
    @Autowired
    private ArticleService articleService;
    
    @PreAuthorize(value = "permitAll()")
    @GetMapping("")
    @ResponseBody
    public ResultDto get() {
        List<Setting> list = settingService.findByModule("about");
        boolean aboutShow = false;
        String contentId = "";
        for (Setting setting : list) {
            if (setting.getName().equals("aboutShow")) {
                if (setting.getContent().equals("1")) {
                    aboutShow = true;
                }
            }
            if (setting.getName().equals("aboutContentId")) {
                contentId = setting.getContent();
            }
        }
        if (aboutShow) {
            Article article = articleService.getById(contentId);
            return ResultDto.ReturnSuccessData(article);
        } else {
            return ResultDto.ReturnSuccess();
        }
    }
}

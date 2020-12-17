package com.imethan.blog.rest;

import com.imethan.blog.document.blog.Article;
import com.imethan.blog.document.blog.Constant;
import com.imethan.blog.dto.ResultDto;
import com.imethan.blog.service.ArticleService;
import com.imethan.blog.service.TagService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Name ArticleRestApi
 * @Description
 * @Author huangyingfeng
 * @Create 2020-12-11 14:49
 */
@RestController
@Log4j2
@RequestMapping(value = "/api/article")
public class ArticleRestApi {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private TagService tagService;

    /**
     * 必须登录才能调用
     *
     * @param article
     * @return
     */
    @PreAuthorize(value = "isAuthenticated()")
    @PostMapping("")
    public ResultDto add(@RequestBody Article article) {
        log.info("post article {}", article);
        return articleService.saveOrUpdate(article);
    }

    /**
     * 必须登录才能调用
     *
     * @param id
     * @return
     */
    @PreAuthorize(value = "isAuthenticated()")
    @DeleteMapping("/{id}")
    public ResultDto delete(@PathVariable String id) {
        log.info("delete article id {}", id);
        return articleService.recycle(id);
    }

    /**
     * 开放授权
     *
     * @param page
     * @param size
     * @param request
     * @return
     */
    @PreAuthorize(value = "permitAll()")
    @GetMapping("/page/{page}/{size}")
    public ResultDto page(@PathVariable Integer page, @PathVariable Integer size, HttpServletRequest request) {
        log.info("get article page page={},size={}", page, size);

        if (size > 100) {
            size = 100;
        }

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("EQ_status", Constant.ARTICLE_STATUS_NORMAL);
        String title = request.getParameter("title");
        String tag = request.getParameter("tag");
        if (StringUtils.isNoneBlank(title)) {
            parameters.put("LIKE_title", title);
        }
        if (StringUtils.isNoneBlank(tag)) {
            parameters.put("LIKE_tag", tag);
        }
        return articleService.page(parameters, page, size);
    }

    /**
     * 开放授权
     *
     * @param id
     * @return
     */
    @PreAuthorize(value = "permitAll()")
    @GetMapping("/{id}")
    public ResultDto get(@PathVariable String id) {
        Article article = articleService.getById(id);
        return ResultDto.ReturnSuccessData(article);
    }

    /**
     * 开放授权
     *
     * @return
     */
    @PreAuthorize(value = "permitAll()")
    @GetMapping("/tag")
    public ResultDto getTag() {
        return tagService.findAll();
    }

}

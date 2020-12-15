package com.imethan.blog.rest;

import com.imethan.blog.document.blog.Article;
import com.imethan.blog.dto.ArticleDto;
import com.imethan.blog.dto.ResultDto;
import com.imethan.blog.service.ArticleService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

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

    @PostMapping("")
    public ResultDto add(@RequestBody Article article) {
        log.info("post article {}",article);
        return articleService.saveOrUpdate(article);
    }
    @GetMapping("/{id}")
    public ResultDto get(@PathVariable String id){
        Article article = articleService.getById(id);
        return ResultDto.ReturnSuccessData(article);
    }

    @DeleteMapping("/{id}")
    public ResultDto delete(@PathVariable  String id){
        log.info("delete article id {}",id);

        return articleService.delete(id);
    }

    @GetMapping("/page/{page}/{size}")
    public ResultDto page(@PathVariable Integer page,@PathVariable Integer size){
        log.info("get article page page={},size={}",page,size);

        return articleService.page(page,size);
    }
}

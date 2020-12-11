package com.imethan.blog.rest;

import com.imethan.blog.document.blog.Article;
import com.imethan.blog.dto.ArticleDto;
import com.imethan.blog.dto.ResultDto;
import com.imethan.blog.service.ArticleService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @Name ArticleRestApi
 * @Description
 * @Author huangyingfeng
 * @Create 2020-12-11 14:49
 */
@Controller
@Log4j2
@RequestMapping(value = "/api/article")
public class ArticleRestApi {

    @Autowired
    private ArticleService articleService;

    @PostMapping("")
    @ResponseBody
    public ResultDto add(@RequestBody ArticleDto articleDto) {

        log.info("post article {}",articleDto);

        Article article = new Article();
        article.setId(articleDto.getId());
        article.setTitle(articleDto.getTitle());
        article.setContent(articleDto.getContent());
        article.setChannelId("100");
        article.setTag(articleDto.getTag());

        return articleService.saveOrUpdate(article);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResultDto delete(@PathVariable  String id){
        log.info("delete article id {}",id);

        return articleService.delete(id);
    }

    @GetMapping("/page/{page}/{size}")
    @ResponseBody
    public ResultDto page(@PathVariable Integer page,@PathVariable Integer size){
        log.info("get article page page={},size={}",page,size);

        return articleService.page(page,size);
    }
}

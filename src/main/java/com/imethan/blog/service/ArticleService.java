package com.imethan.blog.service;

import com.imethan.blog.document.blog.Article;
import com.imethan.blog.dto.ArticleDto;
import com.imethan.blog.dto.ResultDto;

import java.util.Map;

/**
 * @Name ArticleService
 * @Description
 * @Author huangyingfeng
 * @Create 2020-12-11 15:09
 */
public interface ArticleService extends BaseService<Article,String> {


    ResultDto page(Map<String, Object> parameters, Integer pageNo, Integer pageSize);
}

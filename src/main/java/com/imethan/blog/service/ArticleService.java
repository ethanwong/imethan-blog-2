package com.imethan.blog.service;

import com.imethan.blog.document.blog.Article;
import com.imethan.blog.dto.ArticleDto;
import com.imethan.blog.dto.ResultDto;

/**
 * @Name ArticleService
 * @Description
 * @Author huangyingfeng
 * @Create 2020-12-11 15:09
 */
public interface ArticleService extends BaseService<Article,String> {


    ResultDto page(Integer pageNo, Integer pageSize);
}

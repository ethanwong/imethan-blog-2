package com.imethan.blog.service;

import com.imethan.blog.pojo.document.blog.Article;
import com.imethan.blog.pojo.document.blog.Tag;
import com.imethan.blog.pojo.dto.ResultDto;

import java.util.List;
import java.util.Map;

/**
 * @Name ArticleService
 * @Description
 * @Author huangyingfeng
 * @Create 2020-12-11 15:09
 */
public interface ArticleService extends BaseService<Article,String> {


    ResultDto page(Map<String, Object> parameters, Integer pageNo, Integer pageSize);

    List<Article> searchArticleByTag(Tag tag);

    Article findByTitle(String title);


}

package com.imethan.blog.repository;

import com.imethan.blog.pojo.document.blog.Article;
import com.imethan.blog.repository.base.BaseRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @Name ArticleRepository
 * @Description
 * @Author huangyingfeng
 * @Create 2020-12-11 15:10
 */
public interface ArticleRepository extends MongoRepository<Article, String>, BaseRepository<Article,String> {
    List<Article> findByChannelId(String id);

    Article findByTitle(String title);
}

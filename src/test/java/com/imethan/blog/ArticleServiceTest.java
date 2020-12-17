package com.imethan.blog;

import com.imethan.blog.configuration.BlogApplication;
import com.imethan.blog.document.blog.Article;
import com.imethan.blog.repository.ArticleRepository;
import com.imethan.blog.service.ArticleService;
import com.imethan.blog.util.TimeUtils;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Name ArticleServiceTest
 * @Description
 * @Author huangyingfeng
 * @Create 2020-12-17 15:16
 */
@SpringBootTest(classes = BlogApplication.class)
@Log4j2
public class ArticleServiceTest {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleRepository articleRepository;

    @Test
    public void bachSave() {
        Article articleSource = articleService.getById("7d0d346b09014fb48cb2a60b09a2ca28");

        List<Article> articles = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {

            Article article = new Article();

            Long time = System.currentTimeMillis();
            article.setTitle(articleSource.getTitle() + "-" + time);
            article.setContent(articleSource.getContent() + "-" + time);
            article.setTag(articleSource.getTag() + "," + time);
            article.setCreateAt(TimeUtils.dateToString(new Date()));

            articles.add(article);
        }
        articleRepository.saveAll(articles);

    }
}

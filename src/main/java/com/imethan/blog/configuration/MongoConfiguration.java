package com.imethan.blog.configuration;

import com.imethan.blog.document.blog.Tag;
import com.imethan.blog.repository.base.BaseRepositoryImpl;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * @Name MongoConfiguration
 * @Description ${DESCRIPTION}
 * @Author huangyingfeng
 * @Create 2018-09-27 20:12
 */
@Configuration
@EnableMongoRepositories(basePackages = "com.imethan.blog.repository", repositoryBaseClass = BaseRepositoryImpl.class)
public class MongoConfiguration {

    final MongoTemplate mongoTemplate;

    public MongoConfiguration(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initIndicesAfterStartup() {
        //创建所有
        mongoTemplate.indexOps(Tag.class).ensureIndex(new Index().on("name", Sort.Direction.ASC).named("blog_tag_name_index").unique());
    }
}

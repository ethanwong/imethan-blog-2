package com.imethan.blog.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * @Name MongoConfiguration
 * @Description ${DESCRIPTION}
 * @Author huangyingfeng
 * @Create 2018-09-27 20:12
 */
@Configuration
@EnableMongoRepositories(basePackages = "com.imethan.blog.repository")
public class MongoConfiguration {


}

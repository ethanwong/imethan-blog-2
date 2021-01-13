package com.imethan.blog.repository;

import com.imethan.blog.document.SystemLog4j2;
import com.imethan.blog.repository.base.BaseRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Name SystemLogRepository
 * @Description
 * @Author huangyingfeng
 * @Create 2021-01-13 17:45
 */
public interface SystemLogRepository extends MongoRepository<SystemLog4j2, String>, BaseRepository<SystemLog4j2, String> {
}

package com.imethan.blog.repository;

import com.imethan.blog.pojo.document.OpLogType;
import com.imethan.blog.pojo.document.SystemLog4j2;
import com.imethan.blog.pojo.document.SystemOpLog;
import com.imethan.blog.repository.base.BaseRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Name SystemOpLog
 * @Description
 * @Author huangyingfeng
 * @Create 2021-01-25 10:32
 */
public interface SystemOpLogRepository extends MongoRepository<SystemOpLog, String>, BaseRepository<SystemOpLog, String> {

    SystemOpLog findTopByOpLogTypeOrderByIdDesc(OpLogType opLogType);
}

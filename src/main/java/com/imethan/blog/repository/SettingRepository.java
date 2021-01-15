package com.imethan.blog.repository;

import com.imethan.blog.pojo.document.Setting;
import com.imethan.blog.repository.base.BaseRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @Name SettingRepository
 * @Description
 * @Author huangyingfeng
 * @Create 2020-12-30 15:12
 */
public interface SettingRepository extends MongoRepository<Setting, String>, BaseRepository<Setting, String> {
    Setting findByNameAndModule(String name, String module);

    List<Setting> findByModule(String module);
}

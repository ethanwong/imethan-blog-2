package com.imethan.blog.repository;

import com.imethan.blog.pojo.document.blog.Tag;
import com.imethan.blog.repository.base.BaseRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Name TagRepository
 * @Description
 * @Author huangyingfeng
 * @Create 2020-12-11 15:10
 */
public interface TagRepository extends MongoRepository<Tag, String>, BaseRepository<Tag,String> {

    Tag findByName(String name);
}

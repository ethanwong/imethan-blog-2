package com.imethan.blog.repository;

import com.imethan.blog.pojo.document.blog.Channel;
import com.imethan.blog.repository.base.BaseRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Name ChannelRepository
 * @Description
 * @Author huangyingfeng
 * @Create 2020-12-28 11:47
 */
public interface ChannelRepository extends MongoRepository<Channel, String>, BaseRepository<Channel,String> {
    Channel getByName(String name);
}

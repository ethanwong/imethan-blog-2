package com.imethan.blog.service;

import com.imethan.blog.document.blog.Channel;
import com.imethan.blog.dto.ResultDto;

import java.util.Map;

/**
 * @Name ChannelService
 * @Description
 * @Author huangyingfeng
 * @Create 2020-12-28 11:47
 */
public interface ChannelService extends BaseService<Channel,String> {

    ResultDto page(Map<String, Object> parameters, Integer pageNo, Integer pageSize);

    ResultDto list(Map<String, Object> parameters);

    void checkInnerChannel();

    Channel findByName(String channel);

    ResultDto modifyByKeyValue(String id,Map<String, Object> parameter);
}

package com.imethan.blog.service;

import com.alibaba.fastjson.JSONObject;
import com.google.common.cache.*;
import com.imethan.blog.repository.ArticleRepository;
import com.imethan.blog.repository.ChannelRepository;
import com.imethan.blog.repository.TagRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @Name GuavaCacheUtils
 * @Description ${DESCRIPTION}
 * @Author huangyingfeng
 * @Create 2020-03-06 17:15
 */
@Service
@Log4j2
public class StatisticsGuavaCacheService {

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ChannelRepository channelRepository;
    @Autowired
    private TagRepository tagRepository;

    /**
     * 内容数量统计
     */
    public final String CONTENT_TOTAL = "CONTENT_TOTAL";
    /**
     * 标签数据统计
     */
    public final String TAG_TOTAL = "TAG_TOTAL";
    /**
     * 栏目数量统计
     */
    public final String CHANNEL_TOTAL = "CHANNEL_TOTAL";

    private static RemovalListener<String, String> listener = new RemovalListener<String, String>() {
        @Override
        public void onRemoval(RemovalNotification<String, String> notification) {
            log.info("GuavaCache key={},value={} is removed!", notification.getKey(), notification.getValue());
        }
    };

    private CacheLoader<String, String> loader = new CacheLoader<String, String>() {
        @Override
        public String load(String key) throws Exception {
            String result = "";
            /**
             * 数据加载
             */
            if (CONTENT_TOTAL.equals(key)) {
                result = String.valueOf(articleRepository.count());

            } else if (TAG_TOTAL.equals(key)) {
                result = String.valueOf(tagRepository.count());
            } else if (CHANNEL_TOTAL.equals(key)) {
                result = String.valueOf(channelRepository.count());
            }
            log.info("GuavaCache Load key={},value={}", key, result);
            return result;
        }

    };

    private LoadingCache<String, String> loadingCache = CacheBuilder.newBuilder()
//            .maximumSize(3)//设置最大存储
            .recordStats()//开启统计信息开关
            .removalListener(listener)
            .expireAfterWrite(60, TimeUnit.MINUTES)
            .build(loader);//在构建时指定自动加载器

    public String getStatus() {
        return loadingCache.stats().toString();
    }

    /**
     * 获取内容统计
     *
     * @return
     */
    public String getContentTotal() throws ExecutionException {
        return loadingCache.get(CONTENT_TOTAL);
    }

    /**
     * 获取栏目统计
     *
     * @return
     */
    public String getChannelTotal() throws ExecutionException {
        return loadingCache.get(CHANNEL_TOTAL);
    }

    /**
     * 获取标签统计
     *
     * @return
     */
    public String getTagTotal() throws ExecutionException {
        return loadingCache.get(TAG_TOTAL);
    }

    /**
     * 获取首页统计信息
     *
     * @return
     */
    public JSONObject getHomeStatistics() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(CONTENT_TOTAL, this.getContentTotal());
            jsonObject.put(CHANNEL_TOTAL, this.getChannelTotal());
            jsonObject.put(TAG_TOTAL, this.getTagTotal());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("getHomeStatistics error msg={}", e.getMessage());
        }

        return jsonObject;
    }

}

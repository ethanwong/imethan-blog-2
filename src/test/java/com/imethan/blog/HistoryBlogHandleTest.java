package com.imethan.blog;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.imethan.blog.configuration.BlogApplication;
import com.imethan.blog.document.blog.Article;
import com.imethan.blog.document.blog.Channel;
import com.imethan.blog.dto.ResultDto;
import com.imethan.blog.repository.ArticleRepository;
import com.imethan.blog.service.ArticleService;
import com.imethan.blog.service.ChannelService;
import com.imethan.blog.util.UuidUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * @Name HistoryBlogHandleTest
 * @Description
 * @Author huangyingfeng
 * @Create 2020-12-31 17:00
 */
@SpringBootTest(classes = BlogApplication.class)
@Log4j2
public class HistoryBlogHandleTest {

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ChannelService channelService;

    @Value("classpath:static/imethan-blog-histoty.json")
    private Resource resource;

    public void testPath() throws Exception{
        System.out.println(resource.getFile().getPath());
        System.out.println(resource.getFile().getAbsolutePath());
    }

//    @Test
    public void bachImport() throws Exception {

        String data = IOUtils.toString(resource.getInputStream(), Charset.forName("UTF-8"));
        JSONObject json = JSON.parseObject(data);

        JSONArray RECORDS = json.getJSONArray("RECORDS");

        for (int i = 0; i < RECORDS.size(); i++) {
            JSONObject jsonObject = RECORDS.getJSONObject(i);
            Article article = new Article();
            article.setTitle(jsonObject.getString("title"));
            article.setContent(jsonObject.getString("content"));
            article.setCreateAt(jsonObject.getString("createAt"));
            article.setUpdateAt(jsonObject.getString("updateAt"));

            Channel channelDb =  channelService.findByName(jsonObject.getString("channel"));

            if(channelDb == null){
                Channel channel = new Channel();
                channel.setShow(true);
                channel.setType(0);
                channel.setStatus(0);
                channel.setName(jsonObject.getString("channel"));
                ResultDto resultDto = channelService.saveOrUpdate(channel);
                Map<String, String> resultDtoData = (Map<String, String>) resultDto.getData();
                String channelId = resultDtoData.get("id");

                article.setChannelId(channelId);
            }else{
                article.setChannelId(channelDb.getId());
            }

            article.setTag(jsonObject.getString("tag"));
            if (jsonObject.getInteger("show").equals(1)) {
                article.setStatus(0);
            } else {
                article.setStatus(1);
            }
            articleService.saveOrUpdate(article);
        }


    }

}

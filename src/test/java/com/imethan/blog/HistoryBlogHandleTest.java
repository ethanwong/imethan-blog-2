package com.imethan.blog;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.imethan.blog.configuration.BlogApplication;
import com.imethan.blog.document.blog.Article;
import com.imethan.blog.repository.ArticleRepository;
import com.imethan.blog.service.ArticleService;
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

    @Value("classpath:static/imethan-blog-histoty.json")
    private Resource resource;

//    @Test
    public void bachImport() throws Exception{

        String data = IOUtils.toString(resource.getInputStream(), Charset.forName("UTF-8"));
        JSONObject json = JSON.parseObject(data);

        JSONArray RECORDS = json.getJSONArray("RECORDS");



        for(int i=0;i<RECORDS.size();i++){
            JSONObject jsonObject = RECORDS.getJSONObject(i);
//            System.out.println(jsonObject);

            Article article = new Article();
            article.setTitle(jsonObject.getString("title"));
            article.setContent(jsonObject.getString("content"));
            article.setCreateAt(jsonObject.getString("createAt"));
            article.setUpdateAt(jsonObject.getString("updateAt"));
            article.setTag(jsonObject.getString("channel")+","+jsonObject.getString("tag"));


            if(jsonObject.getInteger("show").equals(1)){
                article.setStatus(0);
            }else{
                article.setStatus(1);
            }
            String channelId = "a6f0af5b88f345308ad349171d49a945";
            article.setChannelId(channelId);
            System.out.println(article);
            articleService.saveOrUpdate(article);
        }



    }

}

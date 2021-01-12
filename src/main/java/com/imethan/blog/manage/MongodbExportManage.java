package com.imethan.blog.manage;

import com.imethan.blog.service.EmailService;
import com.imethan.blog.util.MongoExportUtils;
import com.mongodb.client.MongoCursor;
import lombok.extern.log4j.Log4j2;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Name MongodbExportManage
 * @Description
 * @Author huangyingfeng
 * @Create 2021-01-12 17:36
 */
@Service
@Log4j2
public class MongodbExportManage {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private EmailService emailService;

    private final String url = "http://127.0.0.1/email";

    /**
     * 导出数据库
     *
     * @param request
     */
    @Async
    public String export(HttpServletRequest request) {
        String database = mongoTemplate.getDb().getName();
        log.info("database name:{}", database);
        List<String> collections = new ArrayList<>();
        MongoCursor<Document> iterator = mongoTemplate.getDb().listCollections().iterator();
        while (iterator.hasNext()) {
            Document document = iterator.next();
            String collectionName = document.getString("name");

            collections.add(collectionName);
        }
        log.info("collections:{}", collections);
        String targetFileFullName = MongoExportUtils.exportAll(database, collections);

        //生成邮件内容
        String content = this.getEmailContent(request, targetFileFullName);
        log.info("email html content={}", content);
        emailService.sendAttachmentsMail("thehyf@qq.com", "ImEthanBlog2数据自动备份提醒", content, targetFileFullName);

        return targetFileFullName;
    }

    /**
     * 生成邮件内容
     *
     * @param req
     * @param path
     * @return
     */
    private String getEmailContent(HttpServletRequest req, String path) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url + "?path=" + path)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

}

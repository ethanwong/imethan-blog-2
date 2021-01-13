package com.imethan.blog.manage;

import com.imethan.blog.service.EmailService;
import com.imethan.blog.util.MongoExportUtils;
import com.imethan.blog.util.TimeUtils;
import com.mongodb.client.MongoCursor;
import lombok.extern.log4j.Log4j2;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.bson.Document;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
     */
    @Async
    public String export() {
        String database = mongoTemplate.getDb().getName();
        log.warn("database name:{}", database);
        List<String> collections = new ArrayList<>();
        MongoCursor<Document> iterator = mongoTemplate.getDb().listCollections().iterator();
        while (iterator.hasNext()) {
            Document document = iterator.next();
            String collectionName = document.getString("name");

            collections.add(collectionName);
        }
        log.warn("collections:{}", collections);
        String targetFileFullName = MongoExportUtils.exportAll(database, collections);
        //生成邮件内容
        String content = generateMongoDbExportEmailContent(targetFileFullName);
        emailService.sendAttachmentsMail("ethanwong@qq.com", "ImEthanBlog2完成数据自动备份", content, targetFileFullName);

        return targetFileFullName;
    }

    /**
     * 生成邮件提醒内容
     *
     * @param targetFileFullName
     * @return
     */
    private String generateMongoDbExportEmailContent(String targetFileFullName) {
        String content = "" +
                "<div>" +
                "<div>" +
                "   Hey !<br>" +
                "     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ImEthan Blog 2 的MongoDB数据库已经完成自动备份，" +
                " 备份文件在服务器上的路径为:" + targetFileFullName + "，请知悉。" +
                " </div>" +
                " <br>" +
                " <div>" +
                "     <div style='text-align: right;'>" + TimeUtils.dateToString(new Date(), TimeUtils.DATETIME_FORMAT_03) + "&nbsp;&nbsp;ImEthan</div>" +
                " </div>" +
                " </div>";
        log.info("email html content={}", content);
        return content;
    }

}

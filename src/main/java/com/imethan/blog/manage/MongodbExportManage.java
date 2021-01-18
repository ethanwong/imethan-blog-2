package com.imethan.blog.manage;

import com.imethan.blog.service.SettingService;
import com.imethan.blog.util.MongoExportUtils;
import com.imethan.blog.util.TimeUtils;
import com.mongodb.client.MongoCursor;
import lombok.extern.log4j.Log4j2;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

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
    private EmailSenderManage emailSenderManage;
    @Autowired
    private QiniuSDKManage qiniuSDKManage;
    @Autowired
    private SettingService settingService;

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

        //TODO windows环境下的数据备份
        String targetFileFullName = "D:\\timg.jpg";

        if (!System.getProperty("os.name").toLowerCase().contains("windows")) {
            targetFileFullName = MongoExportUtils.exportAll(database, collections);
        } else {
            log.warn("操作系统类型:{},跳过备份处理", System.getProperty("os.name"));
        }

        //上传服务
        String qiniuKey = targetFileFullName.substring(targetFileFullName.lastIndexOf("/") + 1) + "-" + System.currentTimeMillis();
        qiniuSDKManage.uploadFile(targetFileFullName, qiniuKey);

        //生成邮件内容
        String content = generateMongoDbExportEmailContent(targetFileFullName, qiniuKey);
        emailSenderManage.sendAttachmentsMail("ethanwong@qq.com", "ImEthanBlog2完成数据自动备份和上传存储", content, targetFileFullName);

        return targetFileFullName;
    }

    /**
     * 生成邮件提醒内容
     *
     * @param targetFileFullName
     * @return
     */
    private String generateMongoDbExportEmailContent(String targetFileFullName, String qiniuKey) {
        String content = "" +
                "<div>" +
                "<div>" +
                " Hi,ImEthan Blog 2的MongoDB数据库已经完成自动备份，" +
                " 备份文件在服务器上的路径为:" + targetFileFullName + "，上传七牛云存储的文件名称为：" + qiniuKey + "，请知悉。" +
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

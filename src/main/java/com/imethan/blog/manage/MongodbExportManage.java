package com.imethan.blog.manage;

import com.alibaba.fastjson.JSONObject;
import com.imethan.blog.pojo.document.OpLogType;
import com.imethan.blog.pojo.document.SystemOpLog;
import com.imethan.blog.repository.SystemOpLogRepository;
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
import org.springframework.util.DigestUtils;

import java.io.FileInputStream;
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

    @Autowired
    private SystemOpLogRepository systemOpLogRepository;

    /**
     * 导出数据库
     */
    @Async
    public String export() {
        try {
            String database = mongoTemplate.getDb().getName();
            log.warn("database name:{}", database);
            List<String> collections = new ArrayList<>();
            MongoCursor<Document> iterator = mongoTemplate.getDb().listCollections().iterator();
            while (iterator.hasNext()) {
                Document document = iterator.next();
                String collectionName = document.getString("name");
                //不备份滚动的数据
                if (!collectionName.contains("log") && !collectionName.contains("session")) {
                    collections.add(collectionName);
                }
            }
            log.warn("collections:{}", collections);

            //TODO windows环境下的数据备份
            String targetFileFullName = "D:\\timg.jpg";

            if (!System.getProperty("os.name").toLowerCase().contains("windows")) {
                targetFileFullName = MongoExportUtils.exportAll(database, collections);
            } else {
                log.warn("操作系统类型:{},跳过备份处理", System.getProperty("os.name"));
            }

            String md5 = DigestUtils.md5DigestAsHex(new FileInputStream(targetFileFullName));

            if (this.checkBackup(md5)) {
                //需要上传七牛备份

                //上传七牛
                String qiniuKey = targetFileFullName.substring(targetFileFullName.lastIndexOf("/") + 1) + "-" + System.currentTimeMillis() + "-" + md5;
                qiniuSDKManage.uploadFile(targetFileFullName, qiniuKey);

                //生成邮件内容
                String content = generateMongoDbExportEmailContent(true, targetFileFullName, qiniuKey, md5);
                emailSenderManage.sendAttachmentsMail("ethanwong@qq.com", "ImEthanBlog2数据完成本地备份并异地备份", content, targetFileFullName);

                //生成日志
                this.saveOpLog(targetFileFullName, qiniuKey, md5);
                return targetFileFullName;
            } else {
                //无需异地备份
                String content = generateMongoDbExportEmailContent(false, "", "", md5);
                emailSenderManage.sendHtmlMail("ethanwong@qq.com", "ImEthanBlog2数据完成本地备份无异地备份", content);
            }

        } catch (Exception e) {
            log.error("saveOpLog error", e);
        }
        return "";
    }

    /**
     * 检查md5是否有变化
     *
     * @param md5
     * @return
     */
    private boolean checkBackup(String md5) {
        boolean flag = true;
        SystemOpLog systemOpLog = systemOpLogRepository.findTopByOpLogTypeOrderByIdDesc(OpLogType.MONGODB_BAK);
        JSONObject jsonObject = systemOpLog.getLogContent();
        if (jsonObject != null) {
            String oldMd5 = jsonObject.getString("md5");
            if (oldMd5 != null && oldMd5.equals(md5)) {
                flag = false;
            }
        }
        return flag;
    }

    /**
     * 保存操作日志
     *
     * @param targetFileFullName
     * @param qiniuKey
     * @param md5
     */
    private void saveOpLog(String targetFileFullName, String qiniuKey, String md5) {
        SystemOpLog systemOpLog = new SystemOpLog();
        systemOpLog.setOpLogType(OpLogType.MONGODB_BAK);
        JSONObject json = new JSONObject();
        json.put("md5", md5);
        json.put("targetFileFullName", targetFileFullName);
        json.put("qiniuKey", qiniuKey);
        systemOpLog.setLogContent(json);
        systemOpLog.setCreateAt(TimeUtils.dateToString(new Date()));
        systemOpLogRepository.save(systemOpLog);

    }

    /**
     * 生成邮件提醒内容
     *
     * @param targetFileFullName
     * @return
     */
    private String generateMongoDbExportEmailContent(boolean isBak, String targetFileFullName, String qiniuKey, String md5) {
        String content = "";
        if (isBak) {
            content = "" +
                    "<div>" +
                    "<div>" +
                    " Hi,ImEthanBlog2的MongoDB数据本日已经完成自动备份，md5=" + md5 +
                    "，备份文件在服务器上的路径为:" + targetFileFullName + "，上传七牛云存储的文件名称为：" + qiniuKey + "，请知悉。" +
                    " </div>" +
                    " <br>" +
                    " <div>" +
                    "     <div style='text-align: right;'>" + TimeUtils.dateToString(new Date(), TimeUtils.DATETIME_FORMAT_03) + "&nbsp;&nbsp;ImEthan</div>" +
                    " </div>" +
                    " </div>";
        } else {
            content = "" +
                    "<div>" +
                    "<div>" +
                    " Hi,ImEthanBlog2的MongoDB数据本日无新增数据，md5=" + md5 +
                    "，备份文件在服务器上的路径为:" + targetFileFullName + "，没有进行异地备份，请知悉。" +
                    " </div>" +
                    " <br>" +
                    " <div>" +
                    "     <div style='text-align: right;'>" + TimeUtils.dateToString(new Date(), TimeUtils.DATETIME_FORMAT_03) + "&nbsp;&nbsp;ImEthan</div>" +
                    " </div>" +
                    " </div>";
        }

        log.info("email html content={}", content);
        return content;
    }

}

package com.imethan.blog.manage;

import com.alibaba.fastjson.JSONObject;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @Name QiniuSDKUtils
 * @Description
 * @Author huangyingfeng
 * @Create 2021-01-15 11:58
 * @SDK DOC https://developer.qiniu.com/kodo/1239/java
 */
@Log4j2
@Component
public class QiniuSDKManage {

    @Value("${qiniu.accessKey}")
    private String accessKey = "";
    @Value("${qiniu.secretKey}")
    private String secretKey = "";
    @Value("${qiniu.bucket}")
    private String bucket = "imethan-blog-2";
    @Value("${qiniu.domainOfBucket}")
    private String domainOfBucket = "http://qmyn9dpl5.hn-bkt.clouddn.com";

    /**
     * 获取token
     *
     * @return
     */
    private String getToken() {
        Auth auth = Auth.create(accessKey, secretKey);
        return auth.uploadToken(bucket);
    }

    /**
     * 上传文件
     *
     * @param localFilePath 本地文件路径
     * @param key           key为null时，以文件内容的hash值作为文件名
     * @return
     */
    public boolean uploadFile(String localFilePath, String key) {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.huanan());
        UploadManager uploadManager = new UploadManager(cfg);

        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String upToken = getToken();
        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果

            DefaultPutRet putRet = JSONObject.parseObject(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
            log.info("uploadFile to qiniu response key={},response hash={}", putRet.key, putRet.hash);
            return true;
        } catch (QiniuException ex) {
//            Response r = ex.response;
//            System.err.println(r.toString());
            log.error("uploadFile to qiniu error", ex);
//            try {
//                System.err.println(r.bodyString());
//            } catch (QiniuException ex2) {
//                //ignore
//            }
            return false;
        }
    }

    /**
     * 下载文件
     *
     * @param fileName        文件名称
     * @param expireInSeconds 过期时间，秒为单位
     * @throws UnsupportedEncodingException
     */
    public String downloadFile(String fileName, Long expireInSeconds) throws UnsupportedEncodingException {

        String encodedFileName = URLEncoder.encode(fileName, "utf-8").replace("+", "%20");
        String publicUrl = String.format("%s/%s", domainOfBucket, encodedFileName);
        Auth auth = Auth.create(accessKey, secretKey);
        if (expireInSeconds == null) {
            expireInSeconds = 3600L;//1小时，可以自定义链接过期时间
        }
        return auth.privateDownloadUrl(publicUrl, expireInSeconds);
    }

    /**
     * 删除文件
     *
     * @param key
     * @return
     */
    public boolean deleteFile(String key) {
        Configuration cfg = new Configuration(Region.region0());
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            Response response = bucketManager.delete(bucket, key);
            log.info("deleteFile from qiniu response={}", response);
            return true;
        } catch (QiniuException ex) {
//            System.err.println(ex.code());
//            System.err.println(ex.response.toString());
            log.error("deleteFile from qiniu error", ex);
            return false;
        }
    }


}

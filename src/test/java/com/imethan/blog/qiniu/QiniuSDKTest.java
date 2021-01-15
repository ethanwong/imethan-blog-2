package com.imethan.blog.qiniu;

/**
 * @Name QiniuSDKTest
 * @Description
 * @Author huangyingfeng
 * @Create 2021-01-15 15:01
 */
public class QiniuSDKTest {
    public static void main(String[] args) {
//        String localFilePath = "C:\\Users\\ethan\\Pictures\\52.JPG";
//        String fileName = "lsSf-buFssGKWfTXRwOqlLkcisOw";
//
//        QiniuSDKUtils.deleteFile(fileName);
        String targetFileFullName = "/home/mongodb-export/20210114/imethan-blog-2-20210114.tar.gz";
        System.out.println(targetFileFullName.substring(targetFileFullName.lastIndexOf("/") + 1));

    }
}

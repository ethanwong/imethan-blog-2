package com.imethan.blog.pojo.document;

/**
 * @Name OpLogType
 * @Description
 * @Author huangyingfeng
 * @Create 2021-01-25 10:27
 */
public enum OpLogType {

    MONGODB_BAK("MongoDB数据备份"), USER_LOGIN("用户登录");

    private String name;

    private OpLogType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

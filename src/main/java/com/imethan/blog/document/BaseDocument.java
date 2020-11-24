package com.imethan.blog.document;

import org.springframework.data.annotation.Id;

import java.util.UUID;

/**
 * @Name BaseDocument
 * @Description Base
 * @Author huangyingfeng
 * @Create 2020-11-24 17:26
 */
public class BaseDocument {

    /**
     * ID
     */
    @Id
    private String id = UUID.randomUUID().toString().replace("-", "");
    /**
     * 创建者
     */
    private String createUser;
    /**
     * 修改者
     */
    private String updateUser;
    /**
     * 创建时间
     */
    private Long createAt;
    /**
     * 更新时间
     */
    private Long updateAt;
}
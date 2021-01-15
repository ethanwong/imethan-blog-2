package com.imethan.blog.pojo.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.persistence.MappedSuperclass;

import java.util.UUID;

/**
 * @Name BaseDocument
 * @Description Base
 * @Author huangyingfeng
 * @Create 2020-11-24 17:26
 */
@Data
@MappedSuperclass
public class BaseDocument {

    /**
     * ID
     */
    @Id
    private String id;
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
    private String createAt;
    /**
     * 更新时间
     */
    private String updateAt;
}
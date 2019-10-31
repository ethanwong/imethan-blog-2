package com.imethan.blog.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @Name AccountDocument
 * @Description ${DESCRIPTION}
 * @Author huangyingfeng
 * @Create 2018-09-27 20:14
 */
@Data
@Document(collection = "security_account")
public class AccountDocument {

    @Id
    private String id;
    private String username;
    private String password;
}

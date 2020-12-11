package com.imethan.blog.document.rbac;

import com.imethan.blog.document.BaseDocument;
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
public class Account extends BaseDocument {
    private String username;
    private String password;
}

package com.imethan.blog.pojo.document.rbac;

import com.imethan.blog.pojo.document.BaseDocument;
import lombok.Data;
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

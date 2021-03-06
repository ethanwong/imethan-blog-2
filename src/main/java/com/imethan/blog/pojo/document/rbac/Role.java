package com.imethan.blog.pojo.document.rbac;

import com.imethan.blog.pojo.document.BaseDocument;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @Name RoleDocument
 * @Description ${DESCRIPTION}
 * @Author huangyingfeng
 * @Create 2018-09-27 20:19
 */
@Data
@Document(collection = "security_role")
public class Role extends BaseDocument {
    @Id
    private String id;
    private String name;
}

package com.imethan.blog.pojo.document.blog;

import com.imethan.blog.pojo.document.BaseDocument;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @Name Tag
 * @Description
 * @Author huangyingfeng
 * @Create 2020-12-17 15:00
 */
@Data
@Document(collection = "blog_tag")
public class Tag extends BaseDocument {

    private String name;

}

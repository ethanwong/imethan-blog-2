package com.imethan.blog.document.blog;

import com.imethan.blog.document.BaseDocument;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Index;

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

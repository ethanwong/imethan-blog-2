package com.imethan.blog.document.blog;

import com.imethan.blog.document.BaseDocument;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @Name ArticleDocument
 * @Description Article
 * @Author huangyingfeng
 * @Create 2020-11-24 17:23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "blog_article")
public class Article extends BaseDocument {

    private String title;
    private String content;
    private String channelId;
    private String tag;
}

package com.imethan.blog.document.blog;

import com.imethan.blog.document.BaseDocument;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
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
    /**
     * 0-正常，1-草稿，2-回收
     */
    private Integer status;

    @Override
    public String toString() {
        return "Article{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", channelId='" + channelId + '\'' +
                ", tag='" + tag + '\'' +
                ", status='" + status + '\'' +
                "} " + super.toString();
    }
}

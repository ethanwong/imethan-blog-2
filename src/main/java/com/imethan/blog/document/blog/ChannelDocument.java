package com.imethan.blog.document.blog;

import com.imethan.blog.document.BaseDocument;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @Name ChannelDocument
 * @Description Channel
 * @Author huangyingfeng
 * @Create 2020-11-24 17:25
 */
@Data
@Document(collection = "blog_channel")
public class ChannelDocument extends BaseDocument {

    private String name;
    private Boolean show;
    private Integer orderNo;
}

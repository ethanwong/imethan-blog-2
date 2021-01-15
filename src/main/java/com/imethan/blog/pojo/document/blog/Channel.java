package com.imethan.blog.pojo.document.blog;

import com.imethan.blog.pojo.document.BaseDocument;
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
public class Channel extends BaseDocument {

    private String name;
    /**
     * 是否显示
     */
    private Boolean show = true;
    private Integer orderNo;
    /**
     * 0-正常，1-草稿，2-回收
     */
    private Integer status = 0;

    /**
     * 0-标准，1-内置
     */
    private Integer type = 0;

}

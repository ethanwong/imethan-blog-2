package com.imethan.blog.pojo.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @Name Setting
 * @Description
 * @Author huangyingfeng
 * @Create 2020-12-30 15:10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "system_setting")
public class Setting extends BaseDocument {
    //module取值：about,email,qiniu
    public String module;
    public String name;
    public String content;
}

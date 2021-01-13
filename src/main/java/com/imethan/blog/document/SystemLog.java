package com.imethan.blog.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @Name SystemLog
 * @Description
 * @Author huangyingfeng
 * @Create 2021-01-13 10:48
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "system_log")
public class SystemLog extends BaseDocument {
    private Integer type;
    private String content;
}

package com.imethan.blog.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

/**
 * @Name SystemLog
 * @Description
 * @Author huangyingfeng
 * @Create 2021-01-13 10:48
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "system_log4j2")
public class SystemLog4j2 {
    @Id
    private String id;
    private String level;
    private String loggerName;
    private String message;
    private Object source;
    private String threadName;
    private String threadPriority;
    private String millis;
    private String date;
    private String thrown;
    private Object contextMap;
    private Object contextStack;

}

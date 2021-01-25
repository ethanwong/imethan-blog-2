package com.imethan.blog.pojo.document;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @Name SystemOpLog
 * @Description
 * @Author huangyingfeng
 * @Create 2021-01-25 10:26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "system_oplog")
public class SystemOpLog extends BaseDocument {
    private OpLogType opLogType;
    private JSONObject logContent;
}

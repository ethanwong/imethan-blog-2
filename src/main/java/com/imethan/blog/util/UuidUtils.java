package com.imethan.blog.util;

import java.util.UUID;

/**
 * @Name UuidUtils
 * @Description
 * @Author huangyingfeng
 * @Create 2020-12-14 16:53
 */
public class UuidUtils {

    /**
     * UUID
     */
    public static String getUuid(){
        return UUID.randomUUID().toString().replace("-", "");
    }
}

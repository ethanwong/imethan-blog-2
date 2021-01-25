package com.imethan.blog.repository;

import com.alibaba.fastjson.JSONObject;
import com.imethan.blog.configuration.BlogApplication;
import com.imethan.blog.pojo.document.OpLogType;
import com.imethan.blog.pojo.document.SystemOpLog;
import com.imethan.blog.util.TimeUtils;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

/**
 * @Name SystemLogRepositoryTest
 * @Description
 * @Author huangyingfeng
 * @Create 2021-01-25 10:33
 */
@SpringBootTest(classes = BlogApplication.class)
@Log4j2
public class SystemLogRepositoryTest {

    @Autowired
    private SystemOpLogRepository systemOpLogRepository;

    @Test
    public void save() {
        SystemOpLog systemOpLog = new SystemOpLog();
        systemOpLog.setOpLogType(OpLogType.MONGODB_BAK);
        JSONObject json = new JSONObject();
        json.put("md5", "444444444444444444444");
        json.put("file", "asdfasdfasd.tar.gz");
        systemOpLog.setLogContent(json);
        systemOpLog.setCreateAt(TimeUtils.dateToString(new Date()));
        systemOpLogRepository.save(systemOpLog);
    }

    @Test
    public void findTop() {
        SystemOpLog systemOpLog = systemOpLogRepository.findTopByOpLogTypeOrderByIdDesc(OpLogType.MONGODB_BAK);
        System.out.println("systemOpLog=" + systemOpLog);
    }
}

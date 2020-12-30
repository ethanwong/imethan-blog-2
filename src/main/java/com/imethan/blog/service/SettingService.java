package com.imethan.blog.service;

import com.imethan.blog.document.Setting;
import com.imethan.blog.dto.ResultDto;

import java.util.List;

/**
 * @Name SettingService
 * @Description
 * @Author huangyingfeng
 * @Create 2020-12-30 15:12
 */
public interface SettingService extends BaseService<Setting, String> {
    ResultDto batchSaveOrUpdate(List<Setting> t);

    List<Setting> findByModule(String module);
}

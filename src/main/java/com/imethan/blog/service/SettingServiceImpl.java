package com.imethan.blog.service;

import com.imethan.blog.document.Setting;
import com.imethan.blog.dto.ResultDto;
import com.imethan.blog.repository.SettingRepository;
import com.imethan.blog.util.TimeUtils;
import com.imethan.blog.util.UuidUtils;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Name SettingServiceImpl
 * @Description
 * @Author huangyingfeng
 * @Create 2020-12-30 15:13
 */
@Service
@Log4j2
public class SettingServiceImpl implements SettingService {

    @Autowired
    private SettingRepository settingRepository;

    @Override
    public ResultDto saveOrUpdate(Setting setting) {
        try {
            Setting settingDb = settingRepository.findByNameAndModule(setting.getName(), setting.getModule());
            if (settingDb == null) {
                setting.setId(UuidUtils.getUuid());
                setting.setCreateAt(TimeUtils.dateToString(new Date()));
                settingRepository.save(setting);
            } else {
                settingDb.setContent(setting.getContent());
                settingDb.setUpdateAt(TimeUtils.dateToString(new Date()));
                settingRepository.save(settingDb);
            }

        } catch (Exception e) {
            log.error("saveOrUpdate error msg = {}", e.getMessage());
            return ResultDto.ReturnFail(e.getMessage());
        }
        return ResultDto.ReturnSuccess();
    }

    @Override
    public ResultDto delete(String id) {
        return null;
    }

    @Override
    public ResultDto recycle(String id) {
        return null;
    }

    @Override
    public ResultDto update(Setting setting) {
        return null;
    }

    @Override
    public Setting getById(String id) {
        return null;
    }

    @Override
    public ResultDto batchSaveOrUpdate(List<Setting> list) {
        try {
            for (Setting setting : list) {
                this.saveOrUpdate(setting);
            }
            return ResultDto.ReturnSuccess();
        } catch (Exception e) {
            log.error("beachSaveOrUpdate error msg={}", e.getMessage());
            return ResultDto.ReturnFail(e.getMessage());
        }


    }

    @Override
    public List<Setting> findByModule(String module) {
        return settingRepository.findByModule(module);
    }
}

package com.imethan.blog.service;

import com.imethan.blog.pojo.document.Setting;
import com.imethan.blog.pojo.dto.ResultDto;
import com.imethan.blog.repository.SettingRepository;
import com.imethan.blog.util.TimeUtils;
import com.imethan.blog.util.UuidUtils;
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
        Setting result = null;
        try {
            Setting settingDb = settingRepository.findByNameAndModule(setting.getName(), setting.getModule());
            if (settingDb == null) {
                setting.setId(UuidUtils.getUuid());
                setting.setCreateAt(TimeUtils.dateToString(new Date()));
                result = settingRepository.save(setting);
            } else {
                settingDb.setContent(setting.getContent());
                settingDb.setUpdateAt(TimeUtils.dateToString(new Date()));
                result = settingRepository.save(settingDb);
            }

        } catch (Exception e) {
            log.error("saveOrUpdate error", e);
            return ResultDto.ReturnFail(e.getMessage());
        }
        return ResultDto.ReturnSuccessData(result);
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

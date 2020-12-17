package com.imethan.blog.service;

import com.imethan.blog.document.blog.Tag;
import com.imethan.blog.dto.ResultDto;
import com.imethan.blog.repository.TagRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Name TagServiceImpl
 * @Description
 * @Author huangyingfeng
 * @Create 2020-12-17 15:10
 */
@Service
@Log4j2
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Override
    public ResultDto saveOrUpdate(Tag tag) {
        try{
            tagRepository.save(tag);
            return ResultDto.ReturnSuccess();
        }catch (Exception e){
            return ResultDto.ReturnFail(e.getMessage());
        }

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
    public ResultDto update(Tag tag) {
        return null;
    }

    @Override
    public Tag getById(String id) {
        return null;
    }

    @Override
    public ResultDto findAll() {
        return ResultDto.ReturnSuccessData(tagRepository.findAll());
    }
}

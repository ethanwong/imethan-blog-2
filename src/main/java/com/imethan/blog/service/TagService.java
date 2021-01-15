package com.imethan.blog.service;


import com.imethan.blog.pojo.document.blog.Tag;
import com.imethan.blog.pojo.dto.ResultDto;

/**
 * @Name TagService
 * @Description
 * @Author huangyingfeng
 * @Create 2020-12-17 15:09
 */
public interface TagService extends BaseService<Tag,String> {
    ResultDto findAll();

    ResultDto clearTag();
}

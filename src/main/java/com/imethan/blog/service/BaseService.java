package com.imethan.blog.service;

import com.imethan.blog.pojo.dto.ResultDto;

/**
 * @Name BaseService
 * @Description
 * @Author huangyingfeng
 * @Create 2020-12-11 16:05
 */
public interface BaseService<T, ID> {

    ResultDto saveOrUpdate(T t);

    ResultDto delete(String id);

    ResultDto recycle(String id);

    ResultDto update(T t);

    T getById(String id);

}

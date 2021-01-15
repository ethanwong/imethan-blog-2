package com.imethan.blog.service;

import com.imethan.blog.pojo.document.rbac.Account;
import com.imethan.blog.pojo.dto.ResultDto;

/**
 * @Name AccountService
 * @Description ${DESCRIPTION}
 * @Author huangyingfeng
 * @Create 2018-09-28 14:43
 */
public interface AccountService {

    /**
     * 根据用户名称获取账号信息
     *
     * @param username
     * @return
     */
    Account getByUsername(String username);

    /**
     * 检查是否存在root账号，没有则创建
     */
    void checkRootUser();

    /**
     * 修改ROOT账号密码
     * @param password
     * @return
     */
    ResultDto resetRootPassword(String password);
}

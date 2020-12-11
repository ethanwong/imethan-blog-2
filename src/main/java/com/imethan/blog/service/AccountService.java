package com.imethan.blog.service;

import com.imethan.blog.document.rbac.Account;

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
}

package com.imethan.blog.service;

import com.imethan.blog.document.AccountDocument;
import com.imethan.blog.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Name AccountService
 * @Description ${DESCRIPTION}
 * @Author huangyingfeng
 * @Create 2018-09-27 20:52
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public AccountDocument getByUsername(String username) {
        return accountRepository.findByUsername(username);
    }
}

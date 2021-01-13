package com.imethan.blog.service;

import com.imethan.blog.document.rbac.Account;
import com.imethan.blog.dto.ResultDto;
import com.imethan.blog.repository.AccountRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @Name AccountService
 * @Description ${DESCRIPTION}
 * @Author huangyingfeng
 * @Create 2018-09-27 20:52
 */
@Service
@Log4j2
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(4);

    @Override
    public Account getByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    @Override
    public void checkRootUser() {
        try {
            Account account = accountRepository.findByUsername("root");
            if (account == null) {
                Account accountDocument = new Account();
                accountDocument.setUsername("root");
                String password = bCryptPasswordEncoder.encode("root123456");
                accountDocument.setPassword(password);
                accountRepository.save(accountDocument);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public ResultDto resetRootPassword(String password) {
        try {
            log.info("modify root password to {}", password);
            Account account = accountRepository.findByUsername("root");
            String passwordEncode = bCryptPasswordEncoder.encode(password);
            account.setPassword(passwordEncode);
            accountRepository.save(account);
            return ResultDto.ReturnSuccess();
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResultDto.ReturnFail(e.getMessage());
        }

    }
}

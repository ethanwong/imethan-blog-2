package com.imethan.blog;

import com.imethan.blog.document.rbac.Account;
import com.imethan.blog.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class AccountRepositoryTest extends BaseUnitTest {

    @Autowired
    private AccountRepository accountRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(4);

    @Test
    public void testSave() {

        Account accountDocument = new Account();
        accountDocument.setUsername("root");

        String password = bCryptPasswordEncoder.encode("123456");
        accountDocument.setPassword(password);
        accountRepository.save(accountDocument);
    }

}

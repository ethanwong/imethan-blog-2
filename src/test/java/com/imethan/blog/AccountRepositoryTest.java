package com.imethan.blog;

import com.imethan.blog.configuration.BlogApplication;
import com.imethan.blog.document.rbac.Account;
import com.imethan.blog.repository.AccountRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
@SpringBootTest(classes = BlogApplication.class)
@Log4j2
public class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(4);

    @Test
    public void testSave() {

        Account accountDocument = new Account();
        accountDocument.setUsername("admin");

        String password = bCryptPasswordEncoder.encode("admin123456");
        accountDocument.setPassword(password);
        accountRepository.save(accountDocument);
    }

}

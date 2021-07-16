package com.imethan.blog;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @Name BCryptPasswordEncoderTest
 * @Description ${DESCRIPTION}
 * @Author huangyingfeng
 * @Create 2018-09-28 9:13
 */
@Log4j2
public class BCryptPasswordEncoderTest {
    @Test
    public void test() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(4);
        String password = bCryptPasswordEncoder.encode("123456");
        log.info("password is {}", password);
        System.out.println("password is "+password);
    }
}

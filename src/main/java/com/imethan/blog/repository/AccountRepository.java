package com.imethan.blog.repository;

import com.imethan.blog.pojo.document.rbac.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Name AccountRepository
 * @Description ${DESCRIPTION}
 * @Author huangyingfeng
 * @Create 2018-09-27 20:11
 */
public interface AccountRepository extends MongoRepository<Account, String> {
    Account findByUsername(String username);
}

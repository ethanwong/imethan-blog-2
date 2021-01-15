package com.imethan.blog.configuration.security;

import com.imethan.blog.pojo.document.rbac.Account;
import com.imethan.blog.service.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * @Name CustomUserDetailsService
 * @Description ${DESCRIPTION}
 * @Author huangyingfeng
 * @Create 2018-09-27 20:51
 */
@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountServiceImpl accountService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account accountDocument = accountService.getByUsername(username);
        if (accountDocument == null) {
            throw new UsernameNotFoundException("username " + username + " not found");
        }

        String password = accountDocument.getPassword();
        boolean enabled = true;//账号是否启动
        boolean accountNonExpired = true;//是否没有过期
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
        Set<GrantedAuthority> authorities = obtainGrantedAuthorities(accountDocument);
        return new User(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    private Set<GrantedAuthority> obtainGrantedAuthorities(Account accountDocument) {
        Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>();
//        for (Role role : user.getRoles()) {
//            for(Menu menu : role.getMenus()){
//                System.out.println("UserDetailsService menu       name:" + menu.getName());
//                authSet.add(new SimpleGrantedAuthority(menu.getName().trim()));
//            }
//            for (Permission permission : role.getPermissions()) {
//                System.out.println("UserDetailsService permission name:" + permission.getName());
//                authSet.add(new SimpleGrantedAuthority(permission.getName().trim()));
//            }
//        }
        authSet.add(new SimpleGrantedAuthority("HOMEGET"));
//        authSet.add(new SimpleGrantedAuthority("INDEXGET"));
        return authSet;
    }


}

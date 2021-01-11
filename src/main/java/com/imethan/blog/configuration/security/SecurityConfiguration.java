package com.imethan.blog.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

/**
 * @Name WebSecurityConfig
 * @Description ${DESCRIPTION}
 * @Author huangyingfeng
 * @Create 2018-09-27 20:21
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private SecurityFilter securityFilter;


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * ingore是完全绕过了spring security的所有filter，相当于不走spring security
     *
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/static/**", "/webjars/**");
//        web.ignoring().antMatchers("/api/github/**");
    }

    /**
     * permitall没有绕过spring security，其中包含了登录的以及匿名的
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setTargetUrlParameter("redirectTo");
        successHandler.setDefaultTargetUrl("/");
        //开启禁用frame引用
        //DENY：不能被嵌入到任何iframe或frame中
        //SAMEORIGIN：页面只能被本站页面嵌入到iframe或者frame中
        http.headers().frameOptions().sameOrigin();
        //http.headers().httpStrictTransportSecurity().includeSubDomains(true).preload(true);

        http.addFilterBefore(securityFilter, FilterSecurityInterceptor.class)
                .authorizeRequests()
                .antMatchers("/", "/home", "/blog", "/blog/**", "/about", "/gitalk", "/favorite", "/api/**", "/validCode").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/signin")
                .permitAll()
                .successForwardUrl("/console")
                .successHandler(loginSuccessHandler())
                .and()
                .logout()
                .logoutUrl("/signout")
                .logoutSuccessUrl("/signin").permitAll()
                .invalidateHttpSession(true)
                .and().csrf().ignoringAntMatchers("/api/article/upload/image**").and()
                .rememberMe().tokenValiditySeconds(1000 * 60 * 60 * 24);
    }

    @Bean
    public LoginSuccessHandler loginSuccessHandler() {
        return new LoginSuccessHandler();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
        authenticationManagerBuilder.eraseCredentials(false);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(4);
    }

}

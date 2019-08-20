package org.winterframework.sso.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.winterframework.sso.authc.DefaultSecurityManager;
import org.winterframework.sso.authc.MemoryTokenManager;
import org.winterframework.sso.authc.SecurityManager;
import org.winterframework.sso.authc.TokenManager;
import org.winterframework.sso.example.filter.WebSsoFilter;
import org.winterframework.sso.realm.AuthenticatingRealm;
import org.winterframework.sso.realm.MemoryAuthenticatingRealm;
import org.winterframework.sso.session.RedisSessionManager;
import org.winterframework.sso.session.SessionManager;

import javax.servlet.Filter;

/**
 * @Author: YHG
 * @Date: 2019/8/18 10:43
 */
@Configuration
public class WebConfig {

    @Value("${winter.sso.server}")
    private String ssoServer;

    @Bean
    public FilterRegistrationBean filterRegistrationBean(SecurityManager securityManager){
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
        WebSsoFilter filter = new WebSsoFilter();
        filter.setSsoServer(ssoServer);
        filter.addExcludedMapping("/test");
        filter.setSecurityManager(securityManager);
        registration.setFilter(filter);
        registration.addUrlPatterns("/*");
        return registration;
    }

    @Bean
    public SecurityManager securityManager(AuthenticatingRealm authenticatingRealm,
                                           TokenManager tokenManager,
                                           SessionManager sessionManager){
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        securityManager.setAuthenticatingRealm(authenticatingRealm);
        securityManager.setTokenManager(tokenManager);
        securityManager.setSessionManager(sessionManager);
        return securityManager;
    }

    @Bean
    public AuthenticatingRealm authenticatingRealm(){
        return new MemoryAuthenticatingRealm();
    }

    @Bean
    public TokenManager tokenManager(){
        return new MemoryTokenManager();
    }

    @Bean
    public SessionManager sessionManager(){
        return new RedisSessionManager();
    }
}

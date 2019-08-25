package org.winterframework.sso.server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.winterframework.sso.authc.DefaultSecurityManager;
import org.winterframework.sso.authc.SecurityManager;
import org.winterframework.sso.realm.AuthenticatingRealm;
import org.winterframework.sso.realm.MemoryAuthenticatingRealm;

/**
 * @Author: YHG
 * @Date: 2019/8/17 17:17
 */
@Configuration
public class SecurityConfig {

    @Value("${winter.redis.host}")
    private String host;
    @Value("${winter.redis.port}")
    private String port;

    @Bean
    public SecurityManager securityManager(AuthenticatingRealm authenticatingRealm){
        DefaultSecurityManager securityManager = new DefaultSecurityManager(host, port);
        securityManager.setAuthenticatingRealm(authenticatingRealm);
        return securityManager;
    }

    @Bean
    public AuthenticatingRealm authenticatingRealm(){
        return new MemoryAuthenticatingRealm();
    }

}

package org.winterframework.sso.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.winterframework.sso.authc.DefaultSecurityManager;
import org.winterframework.sso.authc.MemoryTokenManager;
import org.winterframework.sso.authc.TokenManager;
import org.winterframework.sso.realm.AuthenticatingRealm;
import org.winterframework.sso.realm.MemoryAuthenticatingRealm;
import org.winterframework.sso.authc.SecurityManager;
/**
 * @Author: YHG
 * @Date: 2019/8/17 17:17
 */
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityManager securityManager(AuthenticatingRealm authenticatingRealm,
                                           TokenManager tokenManager){
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        securityManager.setAuthenticatingRealm(authenticatingRealm);
        securityManager.setTokenManager(tokenManager);
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
}

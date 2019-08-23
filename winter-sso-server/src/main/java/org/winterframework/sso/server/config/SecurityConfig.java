package org.winterframework.sso.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.winterframework.sso.authc.DefaultSecurityManager;
import org.winterframework.sso.authc.SecurityManager;
import org.winterframework.sso.realm.AuthenticatingRealm;
import org.winterframework.sso.realm.MemoryAuthenticatingRealm;
import org.winterframework.sso.session.RedisSessionManager;
import org.winterframework.sso.session.SessionManager;

/**
 * @Author: YHG
 * @Date: 2019/8/17 17:17
 */
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityManager securityManager(AuthenticatingRealm authenticatingRealm,
                                           SessionManager sessionManager){
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        securityManager.setAuthenticatingRealm(authenticatingRealm);
        securityManager.setSessionManager(sessionManager);
        return securityManager;
    }

    @Bean
    public AuthenticatingRealm authenticatingRealm(){
        return new MemoryAuthenticatingRealm();
    }

    @Bean
    public SessionManager sessionManager(){
        return new RedisSessionManager();
    }
}

package org.winterframework.sso.realm;


import org.winterframework.sso.authc.AuthenticationInfo;
import org.winterframework.sso.authc.AuthenticationToken;
import org.winterframework.sso.authc.SimpleAuthenticationInfo;

/**
 * @Author: YHG
 * @Date: 2019/8/17 19:32
 */
public class MemoryAuthenticatingRealm extends AuthenticatingRealm {
    @Override
    AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) {
        if (!"admin".equals(token.getPrincipal())){
            return null;
        }else {
            return new SimpleAuthenticationInfo("admin", "admin");
        }
    }
}

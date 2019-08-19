package org.winterframework.sso.realm;


import org.winterframework.sso.authc.AuthenticationInfo;
import org.winterframework.sso.authc.AuthenticationToken;

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
            return new AuthenticationInfo() {
                @Override
                public Object getPrincipal() {
                    return "admin";
                }

                @Override
                public Object getCredentials() {
                    return "admin";
                }
            };
        }
    }
}

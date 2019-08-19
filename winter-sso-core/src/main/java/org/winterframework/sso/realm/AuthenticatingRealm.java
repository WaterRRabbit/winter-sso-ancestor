package org.winterframework.sso.realm;


import org.winterframework.sso.authc.AuthenticationInfo;
import org.winterframework.sso.authc.AuthenticationToken;
import org.winterframework.sso.exception.AccountException;
import org.winterframework.sso.exception.IncorrectCredentialsException;
import org.winterframework.sso.exception.UnknownAccountException;


/**
 * @Author: YHG
 * @Date: 2019/8/17 16:27
 */
public abstract class AuthenticatingRealm {

    public boolean authenticate(AuthenticationToken token) throws AccountException {
        AuthenticationInfo authenticationInfo = doGetAuthenticationInfo(token);
        if (authenticationInfo==null){
            throw new UnknownAccountException();
        }
        if (!token.getCredentials().equals(authenticationInfo.getCredentials())){
            throw new IncorrectCredentialsException();
        }
        return true;
    }

    /**
     * 认证
     * 模板方法
     *
     * @param token
     * @return
     */
    abstract AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token);
}

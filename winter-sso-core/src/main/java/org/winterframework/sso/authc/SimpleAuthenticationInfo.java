package org.winterframework.sso.authc;

/**
 * @Author: YHG
 * @Date: 2019/8/25 21:12
 */
public class SimpleAuthenticationInfo implements AuthenticationInfo {

    private String username;

    private String password;

    public SimpleAuthenticationInfo(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public Object getPrincipal() {
        return username;
    }

    @Override
    public Object getCredentials() {
        return password;
    }
}

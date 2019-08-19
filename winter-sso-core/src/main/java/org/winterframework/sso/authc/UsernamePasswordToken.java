package org.winterframework.sso.authc;

/**
 * @Author: YHG
 * @Date: 2019/8/17 14:54
 */
public class UsernamePasswordToken implements AuthenticationToken {

    private String username;

    private String password;

    public UsernamePasswordToken(String username, String password) {
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

    public void clear(){
        this.username = null;
        this.password = null;
    }
}

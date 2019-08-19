package org.winterframework.sso.authc;


import org.winterframework.sso.exception.AccountException;
import org.winterframework.sso.realm.AuthenticatingRealm;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: YHG
 * @Date: 2019/8/17 15:55
 */
public class DefaultSecurityManager implements SecurityManager {

    private static final String SSO_TOKEN_NAME = "SSOTOKEN";

    private AuthenticatingRealm authenticatingRealm;
    private TokenManager tokenManager;

    public void setAuthenticatingRealm(AuthenticatingRealm authenticatingRealm) {
        this.authenticatingRealm = authenticatingRealm;
    }

    public void setTokenManager(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    public void init() {

    }

    @Override
    public String login(AuthenticationToken token, HttpServletRequest request,
                      HttpServletResponse response) throws AccountException {
        if (authenticatingRealm.authenticate(token)) {
            String ssoToken = tokenManager.produce(token.getPrincipal());
            response.addCookie(new Cookie(SSO_TOKEN_NAME, ssoToken));
            tokenManager.store(ssoToken);
            return ssoToken;
        }
        return null;
    }

    @Override
    public String loginCheck(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        String ssoToken = null;
        for (Cookie cookie : cookies) {
            if (SSO_TOKEN_NAME.equals(cookie.getName())) {
                ssoToken = cookie.getValue();
            }
        }
        if (ssoToken == null) {
            return null;
        }
        if (tokenManager.check(ssoToken)){
            return ssoToken;
        }
        return null;
    }

    @Override
    public void logout() {

    }

    @Override
    public String produceToken(Object... objects) {
        for (Object object : objects) {
            if (object instanceof String){

            }
        }
        return null;
    }
}

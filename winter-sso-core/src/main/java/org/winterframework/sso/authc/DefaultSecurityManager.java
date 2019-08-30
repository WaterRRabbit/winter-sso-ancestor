package org.winterframework.sso.authc;


import org.winterframework.sso.constant.Constant;
import org.winterframework.sso.exception.AccountException;
import org.winterframework.sso.realm.AuthenticatingRealm;
import org.winterframework.sso.session.RedisSessionManager;
import org.winterframework.sso.session.SessionContext;
import org.winterframework.sso.session.SessionManager;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: YHG
 * @Date: 2019/8/17 15:55
 */
public class DefaultSecurityManager implements SecurityManager {

    private AuthenticatingRealm authenticatingRealm;
    private SessionManager sessionManager;
    private TokenDelegete tokenDelegete;
    private int expiration = -1;

    public DefaultSecurityManager(){
        tokenDelegete = new TokenDelegete();
        sessionManager = new RedisSessionManager();
    }

    public DefaultSecurityManager(String host, String port){
        tokenDelegete = new TokenDelegete();
        sessionManager = new RedisSessionManager(host, port);
    }

    public void setExpiration(int expiration){
        this.expiration = expiration;
    }

    public void setAuthenticatingRealm(AuthenticatingRealm authenticatingRealm) {
        this.authenticatingRealm = authenticatingRealm;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public String login(AuthenticationToken token, HttpServletRequest request,
                        HttpServletResponse response) throws AccountException {
        if (authenticatingRealm.authenticate(token)) {
            // 使用客户端User-Agent作为对称加密密钥
            String sessionId = (String) token.getPrincipal();
            String ssoToken = tokenDelegete.produceToken(request.getHeader("User-Agent"),
                    sessionId);
            response.addCookie(new Cookie(Constant.SSO_TOKEN_NAME, ssoToken));
            SessionContext context = new SessionContext(sessionId, expiration);
            sessionManager.start(context);
            return ssoToken;
        }
        return null;
    }

    @Override
    public String loginCheck(HttpServletRequest request, HttpServletResponse response) {
        String token = tokenDelegete.getToken(request);
        if (token == null) {
            return null;
        }
        String sessionId = tokenDelegete.parseSessionId(token,
                request.getHeader("User-Agent"));
        if (sessionManager.contains(sessionId)) {
            return token;
        }
        return null;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        String token = tokenDelegete.getToken(request);
        if (token != null) {
            String sessionId = tokenDelegete.parseSessionId(token,
                    request.getHeader("User-Agent"));
            sessionManager.removeSession(sessionId);
        }
    }
}

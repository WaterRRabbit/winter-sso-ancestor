package org.winterframework.sso.authc;


import org.winterframework.sso.exception.AccountException;
import org.winterframework.sso.realm.AuthenticatingRealm;
import org.winterframework.sso.session.SessionContext;
import org.winterframework.sso.session.SessionManager;
import org.winterframework.sso.session.SimpleSession;
import org.winterframework.sso.util.DESUtils;

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
    private SessionManager sessionManager;

    public void setAuthenticatingRealm(AuthenticatingRealm authenticatingRealm) {
        this.authenticatingRealm = authenticatingRealm;
    }

    public void setTokenManager(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public void init() {

    }

    @Override
    public String login(AuthenticationToken token, HttpServletRequest request,
                        HttpServletResponse response) throws AccountException {
        if (authenticatingRealm.authenticate(token)) {
            // 使用客户端User-Agent作为对称加密密钥
            String sessionId = (String) token.getPrincipal();
            String ssoToken = this.produceToken(request.getHeader("User-Agent"),
                    sessionId);
            response.addCookie(new Cookie(SSO_TOKEN_NAME, ssoToken));
            SessionContext context = new SessionContext(sessionId);
            sessionManager.start(context);
            tokenManager.store(ssoToken);
            return ssoToken;
        }
        return null;
    }

    @Override
    public String loginCheck(HttpServletRequest request, HttpServletResponse response) {
        String token = getToken(request);
        if (token == null) {
            return null;
        }
        /*if (tokenManager.check(ssoToken)) {
            return ssoToken;
        }*/
        String sessionId = parseSessionId(token, request);
        SimpleSession session = null;
        if ((session = (SimpleSession) sessionManager.getSession(sessionId)) != null) {
            return token;
        }
        return null;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        String ssoToken = getToken(request);
        String sessionId = parseSessionId(ssoToken, request);
        if (ssoToken != null) {
            sessionManager.removeSession(sessionId);
        }
    }

    @Override
    public String produceToken(String mark, Object... objects) {
        StringBuilder original = new StringBuilder();
        for (Object object : objects) {
            if (object instanceof String) {
                if (original.length() != 0) {
                    original.append("#");
                }
                original.append((String) object);
            }
        }
        String token = null;
        try {
            token = DESUtils.encrypt(original.toString(), mark);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;
    }

    /**
     * 获取保存在浏览器端的Token
     *
     * @param request
     * @return
     */
    private String getToken(HttpServletRequest request) {
        String token = null;
        if ((token = request.getParameter("token"))!=null){
            return token;
        }
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (SSO_TOKEN_NAME.equals(cookie.getName())) {
                token = cookie.getValue();
            }
        }
        return token;
    }

    private String parseSessionId(String token, HttpServletRequest request){
        String sessionId = null;
        try {
            sessionId = DESUtils.decrypt(token, request.getHeader("User-Agent"));
        }catch (Exception e){
            e.printStackTrace();
        }
        return sessionId;
    }
}

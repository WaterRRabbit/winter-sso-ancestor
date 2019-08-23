package org.winterframework.sso.authc;


import org.winterframework.sso.exception.AccountException;
import org.winterframework.sso.realm.AuthenticatingRealm;
import org.winterframework.sso.session.SessionContext;
import org.winterframework.sso.session.SessionManager;
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
    private SessionManager sessionManager;

    public void setAuthenticatingRealm(AuthenticatingRealm authenticatingRealm) {
        this.authenticatingRealm = authenticatingRealm;
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
        String sessionId = parseSessionId(token, request.getHeader("User-Agent"));
        if (sessionManager.contains(sessionId)) {
            return token;
        }
        return null;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        String token = getToken(request);
        if (token != null) {
            String sessionId = parseSessionId(token, request.getHeader("User-Agent"));
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
     * 先从参数中获取，没有则在Cookie中寻找
     *
     * @param request
     * @return Token
     */
    private String getToken(HttpServletRequest request) {
        String token;
        if ((token = request.getParameter("token")) != null) {
            return token;
        }
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (SSO_TOKEN_NAME.equals(cookie.getName())) {
                    token = cookie.getValue();
                }
            }
        }
        return token;
    }

    /**
     * 使用User-Agent为密钥解密Token获取sessionId
     *
     * @param token   令牌
     * @param mark
     * @return sessionId
     */
    private String parseSessionId(String token, String mark) {
        String sessionId = null;
        try {
            if (token == null){
                throw new NullPointerException("Token is null");
            }
            sessionId = DESUtils.decrypt(token, mark);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sessionId;
    }
}

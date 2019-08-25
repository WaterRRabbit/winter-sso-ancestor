package org.winterframework.sso.authc;

import org.winterframework.sso.constant.Constant;
import org.winterframework.sso.util.DESUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author: YHG
 * @Date: 2019/8/25 20:15
 */
public class TokenDelegete {

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
    public String getToken(HttpServletRequest request) {
        String token;
        if ((token = request.getParameter("token")) != null) {
            return token;
        }
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (Constant.SSO_TOKEN_NAME.equals(cookie.getName())) {
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
    public String parseSessionId(String token, String mark) {
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

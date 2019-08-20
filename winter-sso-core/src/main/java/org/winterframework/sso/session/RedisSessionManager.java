package org.winterframework.sso.session;

import org.winterframework.sso.util.JedisUtil;

import java.io.Serializable;

/**
 * @Author: YHG
 * @Date: 2019/8/20 10:33
 */
public class RedisSessionManager implements SessionManager {

    private String host = "127.0.0.1";
    private String port = "6379";

    public RedisSessionManager() {
        init();
    }

    public RedisSessionManager(String host, String port) {
        this.host = host;
        this.port = port;
        init();
    }

    private void init() {
        JedisUtil.init("redis://" + host + ":" + port);
    }

    @Override
    public SimpleSession start(SessionContext context) {
        SimpleSession simpleSession = new SimpleSession(context.getSessionId());
        if (context.getExpiration() != 0) {
            simpleSession.setExpiration(context.getExpiration());
        }
        JedisUtil.setObjectValue((String) simpleSession.getId(), simpleSession, simpleSession.getExpiration());
        return simpleSession;
    }

    @Override
    public SimpleSession getSession(Serializable sessionId) {
        return (SimpleSession) JedisUtil.getObjectValue((String) sessionId);
    }

    @Override
    public void removeSession(Serializable sessionId) {
        JedisUtil.del((String) sessionId);
    }
}

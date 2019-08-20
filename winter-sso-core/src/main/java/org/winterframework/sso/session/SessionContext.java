package org.winterframework.sso.session;

import java.io.Serializable;

/**
 * @Author: YHG
 * @Date: 2019/8/20 10:26
 */
public class SessionContext implements Serializable {
    private final static int DEFAULT_EXPIRATION = 1800;
    private Serializable sessionId;
    private int expiration;

    public SessionContext(Serializable sessionId) {
        this(sessionId, DEFAULT_EXPIRATION);
    }

    public SessionContext(Serializable sessionId, int expiration) {
        this.sessionId = sessionId;
        this.expiration = expiration;
    }

    public Serializable getSessionId() {
        return sessionId;
    }

    public void setSessionId(Serializable sessionId) {
        this.sessionId = sessionId;
    }

    public int getExpiration() {
        return expiration;
    }

    public void setExpiration(int expiration) {
        this.expiration = expiration;
    }
}

package org.winterframework.sso.session;

import java.io.Serializable;

/**
 * @Author: YHG
 * @Date: 2019/8/20 10:09
 */
public interface SessionManager {
    Session start(SessionContext var1);
    Session getSession(Serializable var1);
    void removeSession(Serializable var1);
}

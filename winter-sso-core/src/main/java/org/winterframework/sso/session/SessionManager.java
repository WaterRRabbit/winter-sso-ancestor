package org.winterframework.sso.session;

import java.io.Serializable;

/**
 * @Author: YHG
 * @Date: 2019/8/20 10:09
 */
public interface SessionManager {
    /**
     * 开启一个会话
     * 以sessionId作为key存储
     *
     * @param var1
     * @return
     */
    Session start(SessionContext var1);

    /**
     * 根据sessionId获取会话
     *
     * @param var1
     * @return
     */
    Session getSession(Serializable var1);

    /**
     * 判断指定sessionId的会话是否存在
     *
     * @param vae1
     * @return
     */
    boolean contains(Serializable vae1);

    /**
     * 删除一个会话
     *
     * @param var1
     */
    void removeSession(Serializable var1);
}

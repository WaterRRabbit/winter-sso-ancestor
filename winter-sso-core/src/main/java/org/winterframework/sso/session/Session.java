package org.winterframework.sso.session;

import org.winterframework.sso.exception.SessionException;

import java.io.Serializable;
import java.util.Collection;

/**
 * @Author: YHG
 * @Date: 2019/8/20 9:21
 */
public interface Session {

    Serializable getId();

    int getExpiration();

    void setExpiration(int var);

    String getHost();

    Collection<Object> getAttributeKeys() throws SessionException;

    Object getAttribute(Object var1) throws SessionException;

    void setAttribute(Object var1, Object var2) throws SessionException;

    Object removeAttribute(Object var1) throws SessionException;

}

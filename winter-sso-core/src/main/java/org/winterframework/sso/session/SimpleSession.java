package org.winterframework.sso.session;

import org.winterframework.sso.exception.SessionException;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: YHG
 * @Date: 2019/8/20 9:47
 */
public class SimpleSession implements Session, Serializable {

    private static final long serialVersionUID = -7125642695178165650L;
    private final static int DEFAULT_EXPIRATION = 1800;
    private Serializable id;
    private String host;
    private int expiration;
    private Map<Object, Object> attributes;

    public SimpleSession(Serializable id){
        this(id, DEFAULT_EXPIRATION);
    }

    public SimpleSession(Serializable id, int expiration) {
        this.id = id;
        this.expiration = expiration;
    }

    @Override
    public Serializable getId() {
        return this.id;
    }

    public void setId(Serializable id) {
        this.id = id;
    }

    @Override
    public int getExpiration() {
        return this.expiration;
    }

    @Override
    public void setExpiration(int expiration) {
        this.expiration = expiration;
    }

    public void setHost(String host){
        this.host = host;
    }

    @Override
    public String getHost() {
        return this.host;
    }

    public Map<Object, Object> getAttributes() {
        return this.attributes;
    }

    public void setAttributes(Map<Object, Object> attributes) {
        this.attributes = attributes;
    }

    private Map<Object, Object> getAttributesLazy() {
        Map<Object, Object> attributes = this.getAttributes();
        if (attributes == null) {
            attributes = new HashMap<>();
            this.setAttributes(attributes);
        }

        return attributes;
    }

    @Override
    public Collection<Object> getAttributeKeys() throws SessionException {
        Map<Object, Object> attributes = this.getAttributes();
        return attributes == null ? Collections.emptySet() : attributes.keySet();
    }

    @Override
    public Object getAttribute(Object key) throws SessionException {
        Map<Object, Object> attributes = this.getAttributes();
        return attributes == null ? null : attributes.get(key);
    }

    @Override
    public void setAttribute(Object key, Object value) throws SessionException {
        if (value == null) {
            this.removeAttribute(key);
        } else {
            this.getAttributesLazy().put(key, value);
        }
    }

    @Override
    public Object removeAttribute(Object key) throws SessionException {
        Map<Object, Object> attributes = this.getAttributes();
        return attributes == null ? null : attributes.remove(key);
    }

}

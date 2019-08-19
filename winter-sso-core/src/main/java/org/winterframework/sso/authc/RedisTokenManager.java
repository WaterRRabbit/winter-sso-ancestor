package org.winterframework.sso.authc;

/**
 * @Author: YHG
 * @Date: 2019/8/18 16:13
 */
public class RedisTokenManager implements TokenManager {



    @Override
    public boolean store(String token) {
        return false;
    }

    @Override
    public boolean check(String token) {
        return false;
    }

    @Override
    public boolean remove(String token) {
        return false;
    }

    @Override
    public String produce(Object object) {
        return null;
    }
}

package org.winterframework.sso.authc;

/**
 * @Author: YHG
 * @Date: 2019/8/18 16:12
 */
public interface TokenManager{

    boolean store(String token);

    boolean check(String token);

    boolean remove(String token);

    String produce(Object object);
}

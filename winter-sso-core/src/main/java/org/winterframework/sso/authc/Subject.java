package org.winterframework.sso.authc;


import org.winterframework.sso.exception.AccountException;

/**
 * @Author: YHG
 * @Date: 2019/8/17 15:14
 */
public interface Subject {

    void login(AuthenticationToken var1) throws AccountException;

    void logout();

}

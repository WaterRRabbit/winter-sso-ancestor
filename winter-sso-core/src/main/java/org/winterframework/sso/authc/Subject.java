package org.winterframework.sso.authc;


import org.winterframework.sso.exception.AccountException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: YHG
 * @Date: 2019/8/17 15:14
 */
public interface Subject {

    void login(AuthenticationToken var1) throws AccountException;

    void loginCheck(HttpServletRequest request, HttpServletResponse response);

    void logout();

}

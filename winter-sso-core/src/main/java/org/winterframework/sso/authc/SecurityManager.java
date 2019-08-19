package org.winterframework.sso.authc;


import org.winterframework.sso.exception.AccountException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: YHG
 * @Date: 2019/8/17 15:50
 */
public interface SecurityManager{
    /**
     * 登录
     * 成功则返回Token
     *
     * @param token
     * @param request
     * @param response
     * @return
     * @throws AccountException
     */
    String login(AuthenticationToken token, HttpServletRequest request,
               HttpServletResponse response) throws AccountException;

    /**
     * 检查登录状态
     * 已登录则返回Token
     * 未登录则返回null
     *
     * @param request
     * @param response
     * @return
     */
    String loginCheck(HttpServletRequest request,
                    HttpServletResponse response);

    /**
     * 退出
     *
     */
    void logout();

    /**
     * Token生成策略
     *
     * @param objects
     * @return
     */
    String produceToken(Object... objects);
}

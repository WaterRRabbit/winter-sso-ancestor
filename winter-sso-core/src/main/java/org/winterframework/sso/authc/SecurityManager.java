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
    void logout(HttpServletRequest request, HttpServletResponse response);

    /**
     * Token生成策略
     * 使用mark对token进行加密
     * 加密密钥最好为用户的唯一标识
     * 防止token泄露，别拷贝到别的客户端使用
     *
     * @param mark
     * @param objects
     * @return
     */
    String produceToken(String mark, Object... objects);
}

package org.winterframework.sso.authc;

/**
 * @Author: YHG
 * @Date: 2019/8/17 16:32
 */
public interface AuthenticationInfo {
    Object getPrincipal();

    Object getCredentials();
}

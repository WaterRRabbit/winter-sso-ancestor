package org.winterframework.sso.authc;

import java.io.Serializable;

/**
 * @Author: YHG
 * @Date: 2019/8/17 14:52
 */
public interface AuthenticationToken extends Serializable {

    /**
     * 标识
     *
     * @return
     */
    Object getPrincipal();

    /**
     * 凭证
     *
     * @return
     */
    Object getCredentials();

}

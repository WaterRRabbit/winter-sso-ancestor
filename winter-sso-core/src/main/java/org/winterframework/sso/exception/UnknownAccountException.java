package org.winterframework.sso.exception;

/**
 * @Author: YHG
 * @Date: 2019/8/17 15:39
 */
public class UnknownAccountException extends AccountException {
    public UnknownAccountException() {
    }

    public UnknownAccountException(String message) {
        super(message);
    }

    public UnknownAccountException(Throwable cause) {
        super(cause);
    }

    public UnknownAccountException(String message, Throwable cause) {
        super(message, cause);
    }
}

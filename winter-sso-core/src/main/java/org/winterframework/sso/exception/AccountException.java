package org.winterframework.sso.exception;

/**
 * @Author: YHG
 * @Date: 2019/8/17 15:38
 */
public class AccountException extends WinterException {
    public AccountException() {
    }

    public AccountException(String message) {
        super(message);
    }

    public AccountException(Throwable cause) {
        super(cause);
    }

    public AccountException(String message, Throwable cause) {
        super(message, cause);
    }
}

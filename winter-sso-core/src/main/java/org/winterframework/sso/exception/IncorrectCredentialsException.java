package org.winterframework.sso.exception;

/**
 * @Author: YHG
 * @Date: 2019/8/17 15:40
 */
public class IncorrectCredentialsException extends AccountException {
    public IncorrectCredentialsException() {
    }

    public IncorrectCredentialsException(String message) {
        super(message);
    }

    public IncorrectCredentialsException(Throwable cause) {
        super(cause);
    }

    public IncorrectCredentialsException(String message, Throwable cause) {
        super(message, cause);
    }
}

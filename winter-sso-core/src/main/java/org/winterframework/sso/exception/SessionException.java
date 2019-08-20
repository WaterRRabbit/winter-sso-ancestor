package org.winterframework.sso.exception;

/**
 * @Author: YHG
 * @Date: 2019/8/20 9:25
 */
public class SessionException extends WinterException {
    public SessionException() {
    }

    public SessionException(String message) {
        super(message);
    }

    public SessionException(Throwable cause) {
        super(cause);
    }

    public SessionException(String message, Throwable cause) {
        super(message, cause);
    }
}

package org.winterframework.sso.exception;

/**
 * @Author: YHG
 * @Date: 2019/8/17 15:37
 */
public class WinterException extends RuntimeException {

    public WinterException() {
    }

    public WinterException(String message) {
        super(message);
    }

    public WinterException(Throwable cause) {
        super(cause);
    }

    public WinterException(String message, Throwable cause) {
        super(message, cause);
    }

}

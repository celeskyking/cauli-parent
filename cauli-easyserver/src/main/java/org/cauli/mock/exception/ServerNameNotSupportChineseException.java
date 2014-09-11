package org.cauli.mock.exception;

/**
 * Created by tianqing.wang on 2014/8/19
 */
public class ServerNameNotSupportChineseException extends RuntimeException{

    public ServerNameNotSupportChineseException() {
        super();
    }

    public ServerNameNotSupportChineseException(String message) {
        super(message);
    }

    public ServerNameNotSupportChineseException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServerNameNotSupportChineseException(Throwable cause) {
        super(cause);
    }

    protected ServerNameNotSupportChineseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

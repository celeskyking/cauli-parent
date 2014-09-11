package org.cauli.mock.exception;

/**
 * @auther sky
 */
public class SameNameServerException extends MockException{

    public SameNameServerException() {
        super();
    }

    public SameNameServerException(String message) {
        super(message);
    }

    public SameNameServerException(String message, Throwable cause) {
        super(message, cause);
    }

    public SameNameServerException(Throwable cause) {
        super(cause);
    }

    protected SameNameServerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

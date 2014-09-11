package org.cauli.mock.exception;

/**
 * @auther sky
 */
public class UrlConfigError extends MockException {

    public UrlConfigError() {
        super();
    }

    public UrlConfigError(String message) {
        super(message);
    }

    public UrlConfigError(String message, Throwable cause) {
        super(message, cause);
    }

    public UrlConfigError(Throwable cause) {
        super(cause);
    }

    protected UrlConfigError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

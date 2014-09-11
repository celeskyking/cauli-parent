package org.cauli.mock.exception;

/**
 * Created by tianqing.wang on 2014/7/4
 */
public class ActionExecuteException  extends MockException{

    public ActionExecuteException() {
        super();
    }

    public ActionExecuteException(String message) {
        super(message);
    }

    public ActionExecuteException(String message, Throwable cause) {
        super(message, cause);
    }

    public ActionExecuteException(Throwable cause) {
        super(cause);
    }

    protected ActionExecuteException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

package org.cauli.exception;

/**
 * Created by tianqing.wang on 2014/6/6
 */
public class FrameworkBuildException  extends Exception{

    public FrameworkBuildException() {
        super();
    }

    public FrameworkBuildException(String message) {
        super(message);
    }


    public FrameworkBuildException(String message, Throwable cause) {
        super(message, cause);
    }
}

package org.cauli.ui.exception;

/**
 * Created by tianqing.wang on 2014/7/31
 */
public class FrameEnterException extends Exception {

    public FrameEnterException(){
        super();
    }

    public FrameEnterException(String message){
        super(message);
    }

    public FrameEnterException(String message, Exception e){
        super(message,e);
    }
    public FrameEnterException(String message, Throwable e){
        super(message,e);
    }
}

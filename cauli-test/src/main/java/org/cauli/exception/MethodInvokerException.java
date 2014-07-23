package org.cauli.exception;

/**
 * Created by tianqing.wang on 2014/6/17
 */
public class MethodInvokerException extends Exception{

    public MethodInvokerException(){
        super();
    }

    public MethodInvokerException(String message){
        super(message);
    }

    public MethodInvokerException(String message, Exception e){
        super(message,e);
    }
    public MethodInvokerException(String message, Throwable e){
        super(message,e);
    }
}

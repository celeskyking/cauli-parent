package org.cauli.exception;

/**
 * Created by tianqing.wang on 2014/6/6.
 */
public class BeanClassNotMatchException extends RuntimeException{

    public BeanClassNotMatchException(){
        super();
    }

    public BeanClassNotMatchException(String message){
        super(message);
    }

    public BeanClassNotMatchException(String message, Exception e){
        super(message,e);
    }
    public BeanClassNotMatchException(String message, Throwable e){
        super(message,e);
    }
}

package org.cauli.exception;

/**
 * Created by tianqing.wang on 2014/6/17
 */
public class NotFoundNamedMethodException extends CauliException{

    public NotFoundNamedMethodException(){
        super();
    }

    public NotFoundNamedMethodException(String message){
        super(message);
    }

    public NotFoundNamedMethodException(String message, Exception e){
        super(message,e);
    }
    public NotFoundNamedMethodException(String message, Throwable e){
        super(message,e);
    }
}

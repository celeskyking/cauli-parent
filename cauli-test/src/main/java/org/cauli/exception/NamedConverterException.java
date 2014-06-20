package org.cauli.exception;

/**
 * Created by tianqing.wang on 2014/6/19
 */
public class NamedConverterException extends RuntimeException{

    public NamedConverterException(){
        super();
    }

    public NamedConverterException(String message){
        super(message);
    }

    public NamedConverterException(String message, Exception e){
        super(message,e);
    }
    public NamedConverterException(String message, Throwable e){
        super(message,e);
    }
}

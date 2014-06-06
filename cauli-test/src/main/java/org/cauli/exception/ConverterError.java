package org.cauli.exception;

/**
 * Created by tianqing.wang on 2014/6/6
 */
public class ConverterError extends CauliException{

    public ConverterError(){
        super();
    }

    public ConverterError(String message){
        super(message);
    }

    public ConverterError(String message, Exception e){
        super(message,e);
    }
    public ConverterError(String message, Throwable e){
        super(message,e);
    }
}

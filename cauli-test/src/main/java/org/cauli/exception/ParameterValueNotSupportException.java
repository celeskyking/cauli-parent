package org.cauli.exception;

/**
 * Created by tianqing.wang on 2014/6/20
 */
public class ParameterValueNotSupportException extends CauliException{

    public ParameterValueNotSupportException(){
        super();
    }

    public ParameterValueNotSupportException(String message){
        super(message);
    }

    public ParameterValueNotSupportException(String message, Exception e){
        super(message,e);
    }
    public ParameterValueNotSupportException(String message, Throwable e){
        super(message,e);
    }
}

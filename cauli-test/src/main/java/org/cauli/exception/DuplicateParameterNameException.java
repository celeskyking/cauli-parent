package org.cauli.exception;

/**
 * Created by tianqing.wang on 2014/6/17
 */
public class DuplicateParameterNameException extends CauliException{

    public DuplicateParameterNameException(){
        super();
    }

    public DuplicateParameterNameException(String message){
        super(message);
    }

    public DuplicateParameterNameException(String message, Exception e){
        super(message,e);
    }


    public DuplicateParameterNameException(String message, Throwable e){
        super(message,e);
    }
}

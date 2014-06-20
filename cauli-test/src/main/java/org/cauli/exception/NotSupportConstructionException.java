package org.cauli.exception;

/**
 * Created by tianqing.wang on 2014/6/20
 */
public class NotSupportConstructionException extends CauliException{

    public NotSupportConstructionException(){
        super();
    }

    public NotSupportConstructionException(String message){
        super(message);
    }

    public NotSupportConstructionException(String message, Exception e){
        super(message,e);
    }
}

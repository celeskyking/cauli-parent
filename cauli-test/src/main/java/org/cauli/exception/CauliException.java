package org.cauli.exception;

/**
 * Created with IntelliJ IDEA.
 * User: ouamaqing
 * Date: 13-6-28
 * Time: 上午10:48
 * To change this template use File | Settings | File Templates.
 */
public class CauliException extends Exception {

    /**
	 * 
	 */

	public CauliException(){
        super();
    }

    public CauliException(String message){
        super(message);
    }

    public CauliException(String message, Exception e){
        super(message,e);
    }
    public CauliException(String message, Throwable e){
        super(message,e);
    }
}

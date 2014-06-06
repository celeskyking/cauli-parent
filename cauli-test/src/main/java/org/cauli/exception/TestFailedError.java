package org.cauli.exception;

/**
 * @author 王天庆
 * */
public class TestFailedError extends CauliException {
    /**
	 * 
	 */
	public TestFailedError(){
        super();
    }

    public TestFailedError(String message){
        super(message);
    }
    public TestFailedError(String message,Exception e){
        super(message,e);
    }

    public TestFailedError(String message,Throwable e){
        super(message,e);
    }
}

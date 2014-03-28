package org.cauli.junit.exception;

/**
 * @author 王天庆
 * */
public class TestFailedError extends ContorlError {
    /**
	 * 
	 */
	private static final long serialVersionUID = 6284386446873779699L;
	public TestFailedError(){
        super();
    }

    public TestFailedError(String message){
        super(message);
    }
    public TestFailedError(String message,Exception e){
        super(message,e);
    }
}

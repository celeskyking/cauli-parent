package org.cauli.exception;

/**
 * Created by tianqing.wang on 2014/6/6
 */
public class FileGeneratorException extends CauliException{

    public FileGeneratorException() {
        super();
    }

    public FileGeneratorException(String message) {
        super(message);
    }


    public FileGeneratorException(String message, Throwable cause) {
        super(message, cause);
    }

}

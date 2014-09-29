package org.cauli.mock.entity;

import java.io.Serializable;

/**
 * @auther sky
 */
public class DefaultResponse implements Serializable{

    String errorInfo;
    int errorCode;

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}

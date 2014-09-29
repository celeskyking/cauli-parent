package org.cauli.mock.entity;

import java.io.Serializable;

/**
 * @auther sky
 */
public class CallbackResponse implements Serializable{

    int errorCode;

    String errorInfo;

    String result;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}

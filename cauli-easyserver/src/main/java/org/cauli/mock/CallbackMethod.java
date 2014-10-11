package org.cauli.mock;

import java.lang.reflect.Method;

/**
 * Created by tianqing.wang on 2014/10/11
 */
public class CallbackMethod {

    private Method method;
    private int index;

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}

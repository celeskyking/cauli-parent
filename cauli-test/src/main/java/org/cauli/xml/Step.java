package org.cauli.xml;

/**
 * Created by celeskyking on 2014/5/8
 */
public interface Step {

    public Object run();

    public int getIndex();

    public String getStepInfo();

    public Throwable getException();

}

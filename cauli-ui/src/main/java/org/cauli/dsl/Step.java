package org.cauli.dsl;

/**
 * Created by tianqing.wang on 2014/5/7
 */
public interface Step {

    public StepResult run();

    public int getStepNum();

    public Throwable getException();

    public boolean isRunSuccess();

}

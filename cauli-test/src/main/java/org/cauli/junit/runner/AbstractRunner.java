package org.cauli.junit.runner;


import org.cauli.junit.testcase.ITestCase;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.ParentRunner;
import org.junit.runners.model.InitializationError;

import java.util.List;

/**
 * Created by tianqing.wang on 2014/6/23
 */
public class AbstractRunner extends ParentRunner<ITestCase> {
    /**
     * Constructs a new {@code ParentRunner} that will run {@code @TestClass}
     *
     * @param testClass
     */
    protected AbstractRunner(Class testClass) throws InitializationError {
        super(testClass);
    }

    @Override
    protected List getChildren() {
        return null;
    }

    @Override
    protected Description describeChild(ITestCase child) {
        return null;
    }

    @Override
    protected void runChild(ITestCase child, RunNotifier notifier) {

    }
}

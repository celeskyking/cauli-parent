package org.cauli.junit.runner;

import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;

/**
 * Created by tianqing.wang on 2014/6/23
 */
public class AbstractRunner<T> extends Runner{


    @Override
    public Description getDescription() {
        return null;
    }

    @Override
    public void run(RunNotifier notifier) {

    }
}

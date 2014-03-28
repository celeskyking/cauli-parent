package org.cauli;

import org.junit.internal.AssumptionViolatedException;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

/**
 * Created by tianqing.wang on 14-3-17
 */
public abstract class CauliListener extends TestWatcher{
    @Override
    protected abstract void succeeded(Description description) ;

    @Override
    protected abstract void failed(Throwable e, Description description);

    @Override
    protected abstract void skipped(AssumptionViolatedException e, Description description) ;

    @Override
    protected abstract void starting(Description description) ;

    @Override
    protected abstract void finished(Description description);
}

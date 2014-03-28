package org.cauli.junit;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * Created by tianqing.wang on 14-3-17
 */
public abstract class CauliBaseRule implements TestRule{

    @Override
    public abstract Statement apply(Statement base, Description description);
}

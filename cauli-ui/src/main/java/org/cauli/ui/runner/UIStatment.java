package org.cauli.ui.runner;

import org.cauli.junit.statement.InterceptorStatement;
import org.junit.runners.model.FrameworkMethod;

/**
 * Created by tianqing.wang on 14-4-1
 */
public class UIStatment extends InterceptorStatement{


    public UIStatment(FrameworkMethod testMethod, Object target) {
        super(testMethod, target);
    }

}

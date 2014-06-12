package org.cauli.ui.runner;


import org.cauli.junit.statement.Interceptor;
import org.cauli.junit.statement.InterceptorStatement;
import org.cauli.ui.selenium.browser.Auto;
import org.junit.runners.model.FrameworkMethod;


/**
 * Created by celeskyking on 14-3-1
 */
public class CauliUIStatment extends InterceptorStatement{


    public CauliUIStatment(UIFrameworkMethod testMethod, Object target) {
        super(testMethod, target);
    }

    @Override
    public void evaluate() throws Throwable {
        addInterceptor(new BrowserInterceptor());
        super.evaluate();
    }



    public class BrowserInterceptor implements Interceptor{


        @Override
        public void interceptorBefore(InterceptorStatement statement) {

        }

        @Override
        public void interceptorAfter(InterceptorStatement statement) {

        }

        @Override
        public void interceptorAfterForce(InterceptorStatement statement) {

        }

        @Override
        public void interceptorBeforeRetryTimeConfig(InterceptorStatement statement) {

        }
    }
}

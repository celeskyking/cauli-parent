package org.cauli.ui.runner;


import org.cauli.junit.FrameworkMethodWithParameters;
import org.cauli.junit.statement.Interceptor;
import org.cauli.junit.statement.InterceptorStatement;
import org.cauli.ui.selenium.browser.Auto;
import org.cauli.ui.selenium.browser.Engine;
import org.cauli.ui.selenium.listener.ActionListenerProxy;


/**
 * Created by celeskyking on 14-3-1
 */
public class CauliUIStatment extends InterceptorStatement{
    public CauliUIStatment(FrameworkMethodWithParameters methodWithParameters, Object target) {
        super(methodWithParameters, target);
    }


//    public CauliUIStatment(UIFrameworkMethod testMethod, Object target) {
//        super(testMethod, target);
//    }

    @Override
    public void evaluate() throws Throwable {
        addInterceptor(new BrowserInterceptor());
        if(getTestMethod() instanceof UIFrameworkMethod){
            Engine engine = ((UIFrameworkMethod) getTestMethod()).getEngine();
            if(engine!=null){
                Auto.require(engine);
            }
        }
        super.evaluate();
    }

    @Override
    protected void runTestMethod() throws Throwable {
        try{
            super.runTestMethod();
        }catch (Throwable e){
            ActionListenerProxy.getDispatcher().onException();
            throw new Throwable(e);
        }
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

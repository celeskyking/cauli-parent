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
        public void interceptorBefore(FrameworkMethod method, Object test) {
            if(getTestMethod() instanceof UIFrameworkMethod){
                UIFrameworkMethod frameworkMethod = (UIFrameworkMethod) getTestMethod();
                Auto.require(frameworkMethod.getEngine());
            }
        }

        @Override
        public void interceptorAfter(FrameworkMethod method, Object test) {


        }

        @Override
        public void interceptorAfterForce(FrameworkMethod method, Object test) {
            boolean bool = true;
            String boolString = CauliConfigUtil.getInstance().get("browser_fail_auto_close");
            if(boolString!=null&&!"".equals(boolString)){
                bool = Boolean.valueOf(Boolean.valueOf(boolString));
            }
            if(bool){
                if(!Auto.isClosed()){
                    Auto.browser().closeAllWindows();
                }
            }
        }

    }
}

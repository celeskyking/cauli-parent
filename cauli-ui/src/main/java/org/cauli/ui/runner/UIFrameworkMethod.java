package org.cauli.ui.runner;

import org.cauli.junit.FrameworkMethodWithParameters;
import org.cauli.ui.selenium.browser.Engine;

import java.lang.reflect.Method;

/**
 * Created by tianqing.wang on 14-4-1
 */
public class UIFrameworkMethod extends FrameworkMethodWithParameters{

    private Engine engine;
    protected FrameworkMethodWithParameters method;


    public UIFrameworkMethod(Method method,Engine engine){
        super(method);
        this.engine=engine;
    }

    public UIFrameworkMethod(Method method,Object[] parameters,String info,Engine engines){
        super(method,parameters,info);
        this.engine=engines;
    }


    @Override
    public Object invokeExplosively(Object target, Object... parameters) throws Throwable {
        return super.invokeExplosively(target, this.parameters);
    }

    @Override
    public String toString() {
        return super.toString().replace("<null>","")+"["+this.engine+"]";

    }

    public Engine getEngine() {
        return engine;
    }
}

package org.cauli.ui.runner;

import org.cauli.junit.FrameworkMethodWithParameters;
import org.cauli.ui.selenium.browser.Engine;

import java.lang.reflect.Method;

/**
 * Created by tianqing.wang on 14-4-1
 */
public class UIFrameworkMethod extends FrameworkMethodWithParameters{

    private Engine engine;
    protected Object[] parameters;
    protected Integer threadPoolSize;
    protected long timeout;

    public UIFrameworkMethod(Method method, Object[] parameters, Integer threadPoolSize, long timeout, String info) {
        super(method,parameters,threadPoolSize,timeout,info);
    }

    public UIFrameworkMethod(Method method,Engine engine){
        super(method);
        this.engine=engine;
    }

    public UIFrameworkMethod(Method method,Object[] parameters,String info,Engine engines){
        this(method,parameters,null,3000,info);
        this.engine=engines;
    }

    public Integer getThreadPoolSize() {
        return threadPoolSize;
    }

    public long getTimeout() {
        return timeout;
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

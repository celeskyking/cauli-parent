package org.cauli.ui.runner;

import com.google.common.collect.Lists;
import jodd.util.StringUtil;
import org.cauli.instrument.ClassPool;
import org.cauli.junit.FrameworkMethodWithParameters;
import org.cauli.junit.JUnitBaseRunner;
import org.cauli.junit.TestInfoProvider;
import org.cauli.ui.annotation.Action;
import org.cauli.ui.annotation.Require;
import org.cauli.ui.selenium.browser.Engine;
import org.cauli.ui.selenium.listener.ActionListenerProxy;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;
import org.junit.runners.model.TestClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by celeskyking on 14-3-1
 */
public class CauliUIRunner extends JUnitBaseRunner{

    private Logger logger = LoggerFactory.getLogger(CauliUIRunner.class);
    public CauliUIRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }


    @Override
    protected Statement methodInvoker(FrameworkMethod method, Object test) {
        CauliUIStatment statement = new CauliUIStatment((UIFrameworkMethod) method, test);
        return statement;
    }

    @Override
    public void run(RunNotifier notifier) {
        for(Class<?> clazz: ClassPool.getClassPool()){
            if(clazz.isAnnotationPresent(Action.class)){
                ActionListenerProxy.register(clazz);
                logger.info("扫描到动作监听器:{}",clazz.getName());
            }
        }
        super.run(notifier);
    }

    @Override
    protected List<FrameworkMethod> computeTestMethods() {
        List<FrameworkMethod> frameworkMethods=super.computeTestMethods();
        return createUIMethod(frameworkMethods);

    }


    private List<FrameworkMethod> createUIMethod(List<FrameworkMethod> frameworkMethods){
        List<FrameworkMethod> methods = Lists.newArrayList();
        for(FrameworkMethod frameworkMethod:frameworkMethods){
            if(frameworkMethod instanceof FrameworkMethodWithParameters){
                Engine[] engines=getConfigEngines(frameworkMethod);
                for(Engine engine:engines){
                    UIFrameworkMethod method = new UIFrameworkMethod(frameworkMethod.getMethod(),
                            ((FrameworkMethodWithParameters) frameworkMethod).getParameters(),
                            ((FrameworkMethodWithParameters) frameworkMethod).getInfo(),engine);
                    methods.add(method);
                }
            }else if(frameworkMethod instanceof FrameworkMethod){
                Engine[] engines=getConfigEngines(frameworkMethod);
                for(Engine engine:engines){
                    UIFrameworkMethod method = new UIFrameworkMethod(frameworkMethod.getMethod(),engine);
                    methods.add(method);
                }
            }
        }
        return methods;
    }


    private Engine[] getConfigEngines(FrameworkMethod method){
        if(isHaveRequireAnnotation(method)){
            return getRequireBrowsers(method);
        }else{
            String browsers = CauliConfigUtil.getInstance().get("browsers");
            if(browsers!=null&&!"".equals(browsers)){
                String[] strings = StringUtil.split(browsers, "|");
                Engine[] engines= new Engine[strings.length];
                int i=0;
                for(String browser:strings){
                    engines[i]=Engine.valueOf(browser.toUpperCase());
                    i++;
                }
                return engines;
            }else{
                return new Engine[]{Engine.PHANTOMJS};
            }
        }
    }


    private Engine[] getRequireBrowsers(FrameworkMethod method){
        if(method.getMethod().isAnnotationPresent(Require.class)){
            Engine[] engines = method.getAnnotation(Require.class).value();
            return engines;
        }else if(method.getMethod().getDeclaringClass().isAnnotationPresent(Require.class)){
            Engine[] engines = method.getMethod().getDeclaringClass().getAnnotation(Require.class).value();
            return engines;
        }else {
            return new Engine[]{Engine.PHANTOMJS};
        }
    }

    private boolean isHaveRequireAnnotation(FrameworkMethod method){
        if(method.getMethod().isAnnotationPresent(Require.class)||method.getMethod().getDeclaringClass().isAnnotationPresent(Require.class)){
            return true;
        }else{
            return false;
        }
    }

}

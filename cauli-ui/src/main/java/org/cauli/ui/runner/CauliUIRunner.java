package org.cauli.ui.runner;

import com.google.common.collect.Lists;
import jodd.bean.BeanCopy;
import org.cauli.common.instrument.ClassPool;
import org.cauli.common.instrument.ResourceUtil;
import org.cauli.junit.FrameworkMethodWithParameters;
import org.cauli.junit.TestPlan;
import org.cauli.junit.runner.CauliRunner;
import org.cauli.ui.CauliUIConfig;
import org.cauli.ui.annotation.Action;
import org.cauli.ui.annotation.Require;
import org.cauli.ui.config.IConfig;
import org.cauli.ui.selenium.browser.Engine;
import org.cauli.ui.selenium.listener.ActionListenerProxy;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;

/**
 * Created by celeskyking on 14-3-1
 */
public class CauliUIRunner extends CauliRunner{
    private static Logger logger = LoggerFactory.getLogger(CauliUIRunner.class);
    public CauliUIRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }

    CauliUIConfig uiConfig = new CauliUIConfig();

    @Override
    public void run(final RunNotifier notifier) {
        for(Class<?> clazz: ClassPool.getClassPool()){
            if(clazz.isAnnotationPresent(Action.class)){
                ActionListenerProxy.register(clazz);
                logger.info("扫描到动作监听器:{}",clazz.getName());
            }
        }
        super.run(notifier);
    }

    @Override
    protected List<FrameworkMethodWithParameters> computeTestMethods() {
        List<FrameworkMethodWithParameters> frameworkMethods=super.computeTestMethods();
        return createUIMethod(frameworkMethods);

    }


    private List<FrameworkMethodWithParameters> createUIMethod(List<FrameworkMethodWithParameters> frameworkMethods){
        List<FrameworkMethodWithParameters> methods = Lists.newArrayList();
        for(FrameworkMethodWithParameters frameworkMethod:frameworkMethods){
            Engine[] engines=getConfigEngines(frameworkMethod);
            if(engines==null){
                methods.add(frameworkMethod);
            }else{
                for(Engine engine:engines){
                    UIFrameworkMethod method = new UIFrameworkMethod(frameworkMethod.getMethod(),
                            (frameworkMethod).getParameters(),
                            (frameworkMethod).getInfo(),engine);
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
            return uiConfig.getEngines();
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

    @Override
    protected TestPlan init() {
        TestPlan testPlan= super.init();
        BeanCopy.beans(testPlan,uiConfig);
        Set<Class<?>> classes = ResourceUtil.getClassByAssignable(IConfig.class);
        if(classes!=null&&classes.size()>1){
            Class<IConfig> clazz = (Class<IConfig>) classes.iterator().next();
            try {
                IConfig config = clazz.newInstance();
                config.config(uiConfig);
            } catch (Exception e) {
                e.printStackTrace();
            }
            BeanCopy.beans(uiConfig,testPlan);
        }
        return testPlan;
    }

}

package org.cauli.ui.runner;

import org.cauli.instrument.ClassPool;
import org.cauli.junit.JUnitBaseRunner;
import org.cauli.ui.annotation.Action;
import org.cauli.ui.selenium.listener.ActionListenerProxy;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        CauliUIStatment statement = new CauliUIStatment(method, test);
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



}

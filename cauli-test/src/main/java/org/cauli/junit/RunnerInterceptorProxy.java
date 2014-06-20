package org.cauli.junit;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * Created by tianqing.wang on 2014/6/20
 */
public class RunnerInterceptorProxy {

    private static Logger logger = LoggerFactory.getLogger(RunnerInterceptorProxy.class);


    private static List<RunnerInterceptor> runnerInterceptors  = Lists.newArrayList();

    public static RunnerInterceptor dispatcher = (RunnerInterceptor) Proxy.newProxyInstance(RunnerInterceptor.class.getClassLoader(),new Class[]{RunnerInterceptor.class},new InvocationHandler() {
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            for(RunnerInterceptor runnerInterceptor: runnerInterceptors){
                method.invoke(runnerInterceptor,args);
            }
            return null;
        }
    });


    public static void register(RunnerInterceptor runnerInterceptor){
        runnerInterceptors.add(runnerInterceptor);
    }

    public static void unregister(RunnerInterceptor runnerInterceptor){
        runnerInterceptors.remove(runnerInterceptor);
    }

    public static RunnerInterceptor getDispatcher() {
        return dispatcher;
    }


    public static void register(Class<? extends RunnerInterceptor> runnerInterceptorClass){
        if(checkConstruction(runnerInterceptorClass)){
            try {
                logger.info("Add RunnerInterceptor Proxy Class:{}", runnerInterceptorClass.getName());
                runnerInterceptors.add(runnerInterceptorClass.newInstance());
            } catch (Exception e) {
                logger.warn("{}:未能够加载TemplateSource成功...",runnerInterceptorClass.getName());
            }
        }
    }


    public static void register(Class<? extends RunnerInterceptor>[] classes ){
        for(Class<? extends RunnerInterceptor> clazz:classes){
            if(checkConstruction(clazz)){
                try {
                    logger.info("Add RunnerInterceptor Proxy Class:{}", clazz.getName());
                    runnerInterceptors.add(clazz.newInstance());
                } catch (Exception e) {
                    logger.warn("{}:未能够加载TemplateSource成功...",clazz.getName());
                }
            }
        }
    }

    protected static boolean checkConstruction(Class clazz){
        try {
            return clazz.getConstructors().length == 1&&clazz.getConstructor()!=null;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }



}

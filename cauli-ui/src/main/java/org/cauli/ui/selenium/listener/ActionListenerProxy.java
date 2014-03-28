package org.cauli.ui.selenium.listener;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: ouamaqing
 * Date: 13-5-29
 * Time: 下午3:30
 * To change this template use File | Settings | File Templates.
 */
public class ActionListenerProxy {
    private static List<ActionListener> runListeners = new ArrayList<ActionListener>();
    private static ActionListener dispatcher = (ActionListener) Proxy.newProxyInstance(ActionListener.class.getClassLoader(),
            new Class<?>[]{ActionListener.class},
            new InvocationHandler() {
                public Object invoke(Object proxy, Method method, Object[] arg2) throws Throwable {
                    try {
                        for (ActionListener runListener : runListeners) {
                            method.invoke(runListener, arg2);
                        }
                    } catch (InvocationTargetException e) {
                        throw e.getTargetException();
                    }
                    return null;
                }
            });



    public static void unregister(Class<?>clazz){
        ActionListener listener =null;
        for(ActionListener rl : runListeners){
            //RunnerListener listener=null;
            if(rl.getClass().getName().equals(clazz.getName())){
                listener=rl;
            }
        }
        runListeners.remove(listener);

    }

    public static ActionListener getDispatcher() {
        return dispatcher;
    }

    public static void register(Class<?>clazz){
        if(!containsListener(clazz)){
            try {
                runListeners.add((ActionListener) clazz.newInstance());
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

    }

    public static boolean containsListener(Class<?>clazz){
        for(ActionListener rl : runListeners){
            if(rl.getClass().getName().equals(clazz.getName())){
                return true;
            }
        }
        return false;
    }

}

package org.cauli.server.router;

import com.google.common.collect.Maps;
import org.cauli.instrument.ClassPool;
import org.cauli.instrument.ClassUtils;
import org.cauli.server.annotation.Path;
import org.cauli.server.controller.Controller;


import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.Set;

/**
 * Created by tianqing.wang on 2014/9/1
 */
public class Router {

    public void Router(){
        Set<Class<?>> classes= ClassPool.getClassPool();
        for(Class<?> clazz:classes){
            if(ClassUtils.isAssignableFromSubClass(Controller.class,clazz)
                    &&clazz!=Controller.class
                    &&clazz.isAnnotationPresent(Path.class)){
                Path path = clazz.getAnnotation(Path.class);
                add(path.value(), (Class<? extends Controller>) clazz);
            }
        }
    }

    public Map<String,Class<? extends Controller>> routes= Maps.newHashMap();

    public void add(String uriTemplate,Class<? extends Controller> controller){
        this.routes.put(uriTemplate,controller);
    }



    public Controller matchController(String uri) throws Exception{
        for(Map.Entry<String,Class<? extends Controller>> entry:routes.entrySet()){
            if(uri.contains(entry.getKey())){
                Controller controller= routes.get(entry.getKey()).newInstance();
                controller.setRootPath(entry.getKey());
                return controller;
            }
        }
        return null;
    }
}

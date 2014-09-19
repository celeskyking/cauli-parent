package org.cauli.server.router;

import com.google.common.collect.Maps;
import org.cauli.common.instrument.ClassPool;
import org.cauli.common.instrument.ClassUtils;
import org.cauli.server.annotation.Path;
import org.cauli.server.controller.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.Map;
import java.util.Set;

/**
 * Created by tianqing.wang on 2014/9/1
 */
public class Router {

    private Logger logger = LoggerFactory.getLogger(Router.class);

    public  Router(){
        Set<Class<?>> classes= ClassPool.getClassPool();
        for(Class<?> clazz:classes){
            if(ClassUtils.isAssignableFromSubClass(Controller.class, clazz)
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
        try{
            for(Map.Entry<String,Class<? extends Controller>> entry:routes.entrySet()){
                if(uri.startsWith(entry.getKey())){
                    Controller controller= routes.get(entry.getKey()).newInstance();
                    controller.setRootPath(entry.getKey());
                    return controller;
                }
            }
        }catch (Exception e){
            logger.error("没有发现Controller,URI:{}",uri);
            return null;
        }
        return null;
    }
}

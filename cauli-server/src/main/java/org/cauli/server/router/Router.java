package org.cauli.server.router;

import com.google.common.collect.Maps;
import org.cauli.server.controller.Controller;


import java.util.Map;

/**
 * Created by tianqing.wang on 2014/9/1
 */
public class Router {

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

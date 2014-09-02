package org.cauli.server.action;



import com.google.common.collect.Lists;
import org.cauli.server.HttpMethod;
import org.cauli.server.annotation.Path;

import java.util.Arrays;
import java.util.List;


/**
 * Created by tianqing.wang on 2014/9/1
 */
public class ActionBuilder {

    private Action action;
    public ActionBuilder(Action action){
        this.action=action;
    }

    public String getUriTemplate(){
        String rootPath = action.getController().getRootPath();
        if(action.getMethod().isAnnotationPresent(Path.class)){
            Path path = action.getMethod().getAnnotation(Path.class);
            String uri = path.value();
            return rootPath+uri;
        }
        return rootPath+"/"+action.getMethod().getName();
    }

    public List<HttpMethod> getMethods(){
        if(action.getMethod().isAnnotationPresent(Path.class)){
            Path path = action.getMethod().getAnnotation(Path.class);
            HttpMethod[] methods = path.methods();
            return Arrays.asList(methods);
        }else{
            return Lists.newArrayList(HttpMethod.GET,HttpMethod.POST);
        }
    }

}

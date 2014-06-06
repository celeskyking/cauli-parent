package org.cauli.junit;


import com.google.common.collect.Maps;
import org.cauli.junit.anno.Dependency;
import org.junit.runners.model.FrameworkMethod;

import java.util.List;
import java.util.Map;

/**
 * Created by tianqing.wang on 2014/6/5
 */
public class MethodManager {
    private static Map<String,FrameworkMethodWithParameters> methodMap= Maps.newHashMap();


    public static void load(List<FrameworkMethod> methods){
        for(FrameworkMethod method:methods){
            if(method.getMethod().isAnnotationPresent(Dependency.class)){
                Dependency dependency = method.getAnnotation(Dependency.class);
                if(method instanceof FrameworkMethodWithParameters){
                    methodMap.put(dependency.value(), (FrameworkMethodWithParameters) method);
                }

            }
        }
    }

    public static FrameworkMethodWithParameters get(String name){
        return methodMap.get(name);
    }


}

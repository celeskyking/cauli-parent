package org.cauli.junit;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.cauli.junit.anno.Dependency;
import org.cauli.junit.anno.Tag;
import org.junit.runners.model.FrameworkMethod;

import java.util.List;
import java.util.Map;

/**
 * Created by tianqing.wang on 2014/6/5
 */
public class MethodManager {
    private static Map<String,List<FrameworkMethodWithParameters>> methodMap= Maps.newHashMap();


    public static void load(List<FrameworkMethodWithParameters> methods){
        for(FrameworkMethodWithParameters method:methods){
            List<FrameworkMethodWithParameters> list;
            if(methodMap.containsKey(method.getName())){
                list= methodMap.get(method.getName());
                list.add(method);
            }else{
                list=Lists.newArrayList();
                list.add(method);
            }
            methodMap.put(method.getName(),list);
        }
    }

    public static List<FrameworkMethodWithParameters> get(String name){
        return methodMap.get(name);
    }


}

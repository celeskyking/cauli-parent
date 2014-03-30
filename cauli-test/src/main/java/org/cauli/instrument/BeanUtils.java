package org.cauli.instrument;

import jodd.bean.BeanCopy;
import jodd.bean.BeanUtil;

import java.util.Map;

/**
 * Created by celeskyking on 2014/3/30
 */
public class BeanUtils {
    /**Sets Java Bean property forced, throwing an exception on non-existing properties.*/
    public static void setProperty(Object object,String name,Object value){
        BeanUtil.setPropertyForced(object,name,value);
    }
    /**Sets Java Bean property forced, without throwing an exception on non-existing properties.*/
    public static void setPropertySlient(Object object,String name,Object value){
        BeanUtil.setPropertyForcedSilent(object,name,value);
    }

    public static void copyProperties(Object source,Object dest){
        BeanCopy.beans(source,dest);
    }

    public static void copyProperties(Map<String,Object> map,Object dest){
        for(Map.Entry<String,Object> entry:map.entrySet()){
            setPropertySlient(dest,entry.getKey(),entry.getValue());
        }
    }

    public static Object getProperty(Object bean,String name){
        return BeanUtil.getProperty(bean,name);
    }
}

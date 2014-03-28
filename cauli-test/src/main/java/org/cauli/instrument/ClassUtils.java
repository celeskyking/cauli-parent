package org.cauli.instrument;



import com.google.common.collect.Maps;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * Created by celeskyking on 13-12-30
 */
public class ClassUtils {

    public static boolean isSimpleType(Class<?> clazz){
        if(clazz.getName().startsWith("java.")){
            return true;
        }else if (clazz.isPrimitive()){
            return true;
        }else{
            return false;
        }
    }

    public static boolean isListType(Class<?> clazz){
        if(List.class.isAssignableFrom(clazz)){
            return true;
        }else{
            return false;
        }
    }

    public static boolean isMapType(Class<?> clazz){
        if(Map.class.isAssignableFrom(clazz)){
            return true;
        }else{
            return false;
        }
    }

    public static Field[] getFields(Class<?> clazz){
        return clazz.getDeclaredFields();
    }

    public static Method getMethod(Class<?> clazz,String methodName,Class<?>[] types) throws NoSuchMethodException {
        return clazz.getDeclaredMethod(methodName,types);
    }

    public static Method[] getMethods(Class<?> clazz){
        return clazz.getDeclaredMethods();
    }

    public static Map<String,Class<?>> getClassFieldInfo(Class<?> clazz){
        Map<String,Class<?>> map = Maps.newHashMap();
        Field[] fields= clazz.getDeclaredFields();
        for(Field field:fields){
            if(FieldUtils.isSimpleType(field)){
                map.put(field.getName(), field.getType());
            }else{
                FieldTree fieldTree=new FieldTree(field);
                map.putAll(fieldTree.getLeafChainInfos());
            }
        }
        return map;
    }
    /**
     * 判断subclass是否继承自parent
     * */
    public static boolean isAssignableFromSubClass(Class<?> parent,Class<?> subClass){
        return parent.isAssignableFrom(subClass);
    }


}

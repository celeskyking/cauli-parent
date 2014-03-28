package org.cauli.instrument;


import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * @author tianqing.wang
 */
public class FieldUtils {

    public static boolean isSimpleType(Field field){
        if(field.getType().getName().startsWith("java.")){
            return true;
        }else if (field.getType().isPrimitive()){
            return true;
        }else{
            return false;
        }
    }

    public static boolean isListType(Field field){
        if(List.class.isAssignableFrom(field.getType())){
            return true;
        }else{
            return false;
        }
    }

    public static boolean isMapType(Field field){
        if(Map.class.isAssignableFrom(field.getType())){
            return true;
        }else{
            return false;
        }
    }

    public static String[] getGenericTypeContent(Field field){
        Type type = field.getGenericType();
        String classinfo= type.toString();
        classinfo= classinfo.substring(field.getType().getName().length()).replace("<","").replace(">","");
        if(classinfo.contains(",")){
            return StringUtils.stripAll(classinfo.split(","));
        }else{
            return StringUtils.stripAll(new String[]{classinfo});
        }
    }

    public static boolean isFinal(Field field){
        return Modifier.isFinal(field.getModifiers());
    }

}

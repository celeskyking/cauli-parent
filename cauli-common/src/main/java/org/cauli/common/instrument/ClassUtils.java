package org.cauli.common.instrument;



import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.cauli.common.Algorithm.FieldTree;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by celeskyking on 13-12-30
 */
public class ClassUtils {

    public static boolean isSimpleType(Class<?> clazz){
        String name = clazz.getName();
        if(name.startsWith("java.lang")||name.startsWith("java.util")){
            return true;
        }else if (clazz.isPrimitive()){
            return true;
        }else{
            return false;
        }
    }

    private static boolean isPrimitive(Class<?> clazz){
        return clazz.isPrimitive();
    }

    public static boolean isCollectionType(Class<?> clazz){
        if(Collection.class.isAssignableFrom(clazz)){
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

    public static Field getField(Class<?>clazz,String fieldName){
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            return null;
        }
    }

    public static Field getField(Object object,String fieldName){
        try {
            return object.getClass().getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            return null;
        }
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

    public static boolean isInterface(Class<?> clazz){
        return clazz.isInterface();
    }

    public static boolean inInstance(Object object,Class<?> clazz){
        return clazz.isInstance(object);
    }

    public static boolean isAbstract(Class<?> clazz){
        return Modifier.isAbstract(clazz.getModifiers());
    }

    public static boolean isAnnotationPresent(Class<?> clazz,Class<? extends Annotation> annotation){
        return clazz.isAnnotationPresent(annotation);
    }
    /***/
    public static Set<Method> getMethodsContainsAnnatation(Object object,Class<? extends Annotation> anno){
        Set<Method> methodSet = Sets.newHashSet();
        Class<?> clazz =object.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for(Method method:methods){
            if(method.isAnnotationPresent(anno)){
                methodSet.add(method);
            }
        }
        return methodSet;
    }

    public static Set<Field> getFieldContainsAnnatation(Object object,Class<? extends Annotation> anno){
        Set<Field> fieldSet = Sets.newHashSet();
        Class<?> clazz = object.getClass();
        Field[] fields =clazz.getDeclaredFields();
        for(Field field :fields){
            if(field.isAnnotationPresent(anno)){
                fieldSet.add(field);
            }
        }
        return fieldSet;
    }

    public static Set<Method> getMethodsContainsAnnatation(Class clazz,Class<? extends Annotation> anno){
        Set<Method> methodSet = Sets.newHashSet();
        Method[] methods = clazz.getDeclaredMethods();
        for(Method method:methods){
            if(method.isAnnotationPresent(anno)){
                methodSet.add(method);
            }
        }
        return methodSet;
    }

    public static Set<Field> getFieldContainsAnnatation(Class clazz,Class<? extends Annotation> anno){
        Set<Field> fieldSet = Sets.newHashSet();
        Field[] fields =clazz.getDeclaredFields();
        for(Field field :fields){
            if(field.isAnnotationPresent(anno)){
                fieldSet.add(field);
            }
        }
        return fieldSet;
    }

    public static Set<Method> getMethodsByName(Object object,String methodName){
        Set<Method> methodSet = Sets.newHashSet();
        Class<?> clazz = object.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for(Method method:methods){
            if(method.getName().equals(methodName)){
                methodSet.add(method);
            }
        }
        return methodSet;
    }

    public static Set<Method> getMethodsByName(Class clazz,String methodName){
        Set<Method> methodSet = Sets.newHashSet();
        Method[] methods = clazz.getDeclaredMethods();
        for(Method method:methods){
            if(method.getName().equals(methodName)){
                methodSet.add(method);
            }
        }
        return methodSet;
    }

    public static <T> T newInstance(Class<T> clazz,Object... objects) throws Exception {
        if(clazz.isInterface()){
            throw new InstantiationError("无法对接口进行实例化,class:"+clazz.getName());
        }else if(isAbstract(clazz)){
            throw new InstantiationError("无法对抽象类进行实例化,class:"+clazz.getName());
        }else if(isPrimitive(clazz)){
            throw new InstantiationError("无法对基本类型进行实例化,clazz:"+clazz.getName());
        }

        if(objects==null){
            return clazz.newInstance();
        }else{
            Class[] types = new Class[objects.length];
            for(int i=0;i<objects.length;i++){
                types[i]=objects[i].getClass();
            }

            Constructor<T> constructor = clazz.getConstructor(types);
            return constructor.newInstance(objects);
        }
    }

    public static boolean isPublic(Class<?> clazz){
        return Modifier.isPublic(clazz.getModifiers());
    }

    public static boolean isStatic(Class<?> clazz){
        return Modifier.isStatic(clazz.getModifiers());
    }

    public static boolean isProtected(Class<?> clazz){
        return Modifier.isProtected(clazz.getModifiers());
    }

    public static boolean isPrivate(Class<?> clazz){
        return Modifier.isPrivate(clazz.getModifiers());
    }





}

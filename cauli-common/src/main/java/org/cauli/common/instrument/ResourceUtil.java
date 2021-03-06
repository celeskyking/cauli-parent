package org.cauli.common.instrument;

import com.google.common.collect.Sets;
import com.google.common.io.Resources;

import java.io.File;
import java.lang.Class;import java.lang.String;import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * @auther sky
 */
public class ResourceUtil {


    public static File getFileFromClassPath(String fileName){
        try{
            return new File(Resources.getResource(fileName).getFile());
        }catch (Exception e){
            return null;
        }

    }

    public static Set<Class<?>> getClassContainsAnnotation(Class<? extends Annotation> annotation){
        Set<Class<?>> classes = ClassPool.getClassPool();
        Set<Class<?>> annoClasses = Sets.newHashSet();
        for(Class<?> clazz:classes){
            if(clazz.isAnnotationPresent(annotation)){
                annoClasses.add(clazz);
            }
        }
        return annoClasses;
    }


    public static Set<Class<?>> findClasses(final String baseName){
        return ClassPool.getClassPool(baseName);
    }

    public static Set<Class<?>> getClassByAssignable(Class<?> clazz){
        Set<Class<?>> classes = ClassPool.getClassPool();
        Set<Class<?>> newClasses = Sets.newHashSet();
        for(Class<?> cls:classes){
            if(ClassUtils.isAssignableFromSubClass(clazz,cls)&&cls!=clazz) {
                newClasses.add(cls);
            }
        }
        return newClasses;
    }


}

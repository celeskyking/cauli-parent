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
        return new File(Resources.getResource(fileName).getFile());
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


}

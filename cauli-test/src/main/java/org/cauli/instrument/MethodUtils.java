package org.cauli.instrument;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by tianqing.wang on 14-3-20
 */
public class MethodUtils {

    public static boolean isParameterAnnotationPresent(Method method,Class<?> clazz){
        Annotation[][] annotations = method.getParameterAnnotations();
        for(Annotation[] signAnno:annotations){
            for (Annotation annotation:signAnno){
                if(annotation.annotationType()==clazz){
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isParameterAnnotationPresent(Method method,Class<?> clazz,int paramterIndex){
        Annotation[][] annotations = method.getParameterAnnotations();
        int i=0;
        for(Annotation[] signAnno:annotations){
            if(i==paramterIndex){
                for (Annotation annotation:signAnno){
                    if(annotation.annotationType()==clazz){
                        return true;
                    }
                }
            }
            i++;
        }
        return false;
    }
    /**
     * @param method 获取注解的方法
     * @param clazz 注解类
     * @param index 方法参数序列
     * */
    public static <T> T getParameterAnnotation(Method method,Class<T> clazz,int index){
        Annotation[] annotations = method.getParameterAnnotations()[index];
        for (Annotation annotation:annotations){
            if(annotation.annotationType()==clazz){
                return (T) annotation;
            }
        }
        return null;
    }
}

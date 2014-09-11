package org.cauli.mock.util;

import org.cauli.mock.core.convert.ConvertManager;
import org.cauli.mock.entity.ParameterValuePairs;
import org.cauli.instrument.MethodUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by tianqing.wang on 2014/7/14
 */
public class ActionMethodUtil {

    private static Logger logger = LoggerFactory.getLogger(ActionMethodUtil.class);


    public static Object[] parse(Method method,ParameterValuePairs valuePairs){
        Class<?>[] paramClasses = method.getParameterTypes();
        if(paramClasses==null||paramClasses.length==0){
            throw new RuntimeException("Action方法不能够不包含参数");
        }
        ConvertManager.ConvertMap convertMap = valuePairs.getConvertMap();
        Object object[] = new Object[paramClasses.length];
        for(int i=0;i<paramClasses.length;i++){
            Class<?> key = getMethodParseType(method,i);
            logger.info("class->key:{}",key.getName());
            if(convertMap.contains(key)){
                object[i]= convertMap.getConvertExecuter(key).execute(key,valuePairs);
            }else{
                logger.warn("未找到该类型的参数,无法进行值注入。type:{}",key.getName());
                object[i]=0;
            }
        }
        return object;
    }


    private static boolean isMethodParameterHasAnnatation(Method method ,int index){
        Annotation[][] annotations = method.getParameterAnnotations();
        if(annotations[index].length>0){
            return true;
        }else{
            return false;
        }
    }


    private static Class<?> getMethodParseType(Method method ,int index){
        Class<?> clazz;
        if(isMethodParameterHasAnnatation(method,index)){
            clazz= MethodUtils.getParameterOnlyAnnotationType(method,index);
        }else{
            clazz= method.getParameterTypes()[index];
        }
        logger.info("获取到Method: {}的第{}个参数的类型或者注解为:{}",method.getName(),index,clazz.getName());
        return clazz;
    }


}

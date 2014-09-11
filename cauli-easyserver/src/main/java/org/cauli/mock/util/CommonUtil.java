package org.cauli.mock.util;

import jodd.util.StringUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Created by tianqing.wang on 2014/8/12
 */
public class CommonUtil {

    public static  int getMethodParameterCount(Method method){
        Class<?>[] classes = method.getParameterTypes();
        if(classes!=null){
            return classes.length;
        }else{
            return 0;
        }
    }


    public static  int getConstructorParameterCount(Constructor constructor){
        Class<?>[] classes = constructor.getParameterTypes();
        if(classes!=null){
            return classes.length;
        }else{
            return 0;
        }
    }


    public static boolean checkContainsChinese(String str){
        if(StringUtil.isEmpty(str)){
            return false;
        }
        return str.getBytes().length!=str.length();
    }

}

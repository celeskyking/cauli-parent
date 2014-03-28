package org.cauli.junit.commons;

/**
 * Created with IntelliJ IDEA.
 * User: ouamaqing
 * Date: 13-6-26
 * Time: 下午1:09
 * To change this template use File | Settings | File Templates.
 */
public class StringUtils {

    public static Integer toInteger(String str){
        Integer integer=null;
        try{
            integer=Integer.parseInt(str);
        }catch (Exception e){
            throw new RuntimeException("参数不能转化为数字");
        }
        return integer;
    }

    public static Boolean toBoolean(String str){
        str=str.trim();
        if ("true".equalsIgnoreCase(str))
            return true;
        else if ("false".equalsIgnoreCase(str))
            return false;
        else
            throw new RuntimeException("Not a boolean value");
    }

    public static Double toDouble(String str){
        Double aDouble=null;
        try{
            aDouble=Double.parseDouble(str);
        }catch (Exception e){
            throw new RuntimeException("参数不能转化为数字");
        }
        return aDouble;
    }

    public static Float toFloat(String str){
        Float aFloat=null;
        try{
            aFloat=Float.parseFloat(str);
        }catch (Exception e){
            throw new RuntimeException("参数不能转化为Float类型");
        }
        return aFloat;
    }

    public static Long toLong(String str){
        Long aLong =null;
        try{
            aLong=Long.parseLong(str);
        }catch (Exception e){
            throw new RuntimeException("参数不能转化为Long类型");
        }
        return aLong;
    }

}

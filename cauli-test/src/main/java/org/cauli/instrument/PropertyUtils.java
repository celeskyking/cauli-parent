package org.cauli.instrument;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by tianqing.wang on 14-2-25
 */
public class PropertyUtils {

    private static Logger logger = LoggerFactory.getLogger(PropertyUtils.class);
    //暂时不支持list属性下面已经有list元素....
    public static void setProperty(Object obj, String field, Object object) throws InvocationTargetException, IllegalAccessException {
        if(!field.contains("[")&&!field.contains("(")){
            BeanUtils.setProperty(obj,field,object);
        }else if(field.contains("[")){
            parseListProperty(obj,field,object);
        }else if(field.contains("(")){
            BeanUtils.copyProperty(obj,field,object);
        }
    }


    private static void parseListProperty(Object obj, String field, Object value)  {
        String fieldName;
        String frontFieldName = StringUtils.substringBefore(field,"[");
        if(StringUtils.substringBefore(field,"[").contains(".")){
            fieldName = StringUtils.substringAfterLast(StringUtils.substringBefore(field, "["), ".");
        }else{
            fieldName=StringUtils.substringBefore(field,"[");
        }
        Integer index = Integer.parseInt(StringUtils.substringBetween(field,"[","]"));
        if(field.contains("].")){
            String _field = StringUtils.substringAfterLast(field,"].");
            Field f = null;
            try {
                f = getFieldObject(obj, frontFieldName).getClass().getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                logger.error(">>{} ->查找属性{}的时候出现了错误...", field, fieldName, e);

            } catch (Exception e){
                e.printStackTrace();
           } 
            //获取list的泛型类型
            Class<?> clazz = f.getType();
            if(ClassUtils.isListType(clazz)){
                Class<?> type = null;
                try {
                    type = Class.forName(FieldUtils.getGenericTypeContent(f)[0]);
                    List list = (List) getFieldValue(obj,frontFieldName);
                    Object object = null;
                    //复杂的list分析，如果请求的index大于list的长度，则创造长度，然后初始化bean
                    //如果小于但是取出来的值为null则继续初始化，如果不为null则在原有的值上面赋值
                    if(index>=0){
                        if(list.size()==0){
                            for(int i=0;i<index+1;i++){
                                list.add(i,null);
                            }
                            object = type.newInstance();
                            initBean(object);
                        }else{
                            if(index>list.size()){
                                if(index-list.size()>1){
                                    for(int j=index-list.size();j<=index;j++){
                                        list.add(j,null);

                                    }
                                    object = type.newInstance();
                                    initBean(object);
                                }
                            }else if(index<=list.size()){
                                if(list.get(index)!=null){
                                    object=list.get(index);
                                }else{
                                    object = type.newInstance();
                                    initBean(object);
                                }
                            }
                        }
                    }
                    if(object==null){
                        logger.error("Object is wrong");
                    }
                    BeanUtils.setProperty(object,_field,value);
                    list.set(index, object);

                } catch (Exception e) {
                    logger.error("给List{}赋值的时候出现了错误...",obj.getClass().getName());
                    throw new RuntimeException(e);
                }
            }
        }else{
            try {
                List list = (List) getFieldValue(obj,frontFieldName);
                list.set(index, value);
            } catch (Exception e) {
                logger.error("给List{}赋值的时候出现了错误...", obj.getClass().getName());
                throw new RuntimeException(e);
             
             }
        }

    }

    public static void initBean(Object obj) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class<?> clazz = obj.getClass();
        for(Field field:ClassUtils.getFields(clazz)){
            field.setAccessible(true);
            if(FieldUtils.isSimpleType(field)){
                if(field.getType().getName().contains("BigDecimal")){
                    field.set(obj,new BigDecimal("0.00"));
                }else if(ClassUtils.isListType(field.getType())){
                    List list = Lists.newArrayList();
                    field.set(obj, list);
                }else if(ClassUtils.isMapType(field.getType())){
                    field.set(obj, Maps.newHashMap());
                }else{
                    continue;
                }
            }else {
                Object childObj=Class.forName(field.getType().getName()).newInstance();
                field.setAccessible(true);
                field.set(obj,childObj);
                initBean(childObj);
            }
        }
    }

    private static String getGetBeanMethodName(Field field){
        String fieldName = field.getName();
        fieldName=StringUtils.substring(fieldName,0,1).toUpperCase()+StringUtils.substring(fieldName,1);
        String methodName = "get"+fieldName;
        return methodName;
    }
    private static String getGetBeanMethodName(String fieldName){
        fieldName=StringUtils.substring(fieldName,0,1).toUpperCase()+StringUtils.substring(fieldName,1);
        String methodName = "get"+fieldName;
        return methodName;
    }

    private static String getSetBeanMethodName(Field field){
        String fieldName = field.getName();
        fieldName=StringUtils.substring(fieldName,0,1).toLowerCase()+StringUtils.substring(fieldName,1);
        String methodName = "set"+fieldName;
        return methodName;
    }
    private static String getSetBeanMethodName(String fieldName){
        fieldName=StringUtils.substring(fieldName,0,1).toLowerCase()+StringUtils.substring(fieldName,1);
        String methodName = "set"+fieldName;
        return methodName;
    }

    public static Object getFieldValue(Object object,String field) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Object obj = object;
        if(field.contains(".")){
            String[] fields = StringUtils.split(field,".");
            for(int i=0;i<fields.length;i++){
                obj=MethodUtils.invokeMethod(obj,getGetBeanMethodName(fields[i]));
            }
            return obj;
        }else{
            return MethodUtils.invokeMethod(obj,getGetBeanMethodName(field));
        }
    }

    public static Object getFieldObject(Object object,String field) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Object obj = object;
        if(field.contains(".")){
            String[] fields = StringUtils.split(field,".");
            for(int i=0;i<fields.length-1;i++){
                obj=MethodUtils.invokeMethod(obj,getGetBeanMethodName(fields[i]));
            }
            return obj;
        }else{
            return obj;
        }
    }



}

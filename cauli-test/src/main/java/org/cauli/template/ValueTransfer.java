package org.cauli.template;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;
import org.cauli.exception.BeanClassNotMatchException;
import org.cauli.instrument.BeanUtils;
import org.cauli.junit.PairParameter;
import org.cauli.junit.build.VauleUtil;
import org.cauli.pairwise.core.ParameterValuePair;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;

/**
 * Created by tianqing.wang on 2014/6/19
 */
public class ValueTransfer {
    private final static String DEFAULT_TEMPLATE_NAME = "_default_name_";


    public static Map<String,Object> models = Maps.newHashMap();


    public static void addModels(Map<String,Object> map){
        if(null!=map){
            models.putAll(map);
        }
    }

    public static boolean isContains(String name){
        return models.containsKey(name);
    }

    public static boolean checkNameFormat(String name){
        if(name.contains("${")){
            return name.contains("}");
        }else if(name.contains("}")){
            return name.contains("${");
        }
        return false;
    }

    public static boolean isContainsMethod(String value){
        if(value.contains("(")&&value.contains(")")){
            return true;
        }else{
            return false;
        }
    }


    public static void register(String name,Object model){
        models.put(name,model);
    }


    public static String getValue(String name,PairParameter pairParameter,Map<String,Class<?>>paramTyps) throws IOException, TemplateException {
        if(checkNameFormat(name)){
            if(isMatchOtherParameter(name,pairParameter)){
                //List<String> depencyNames = getDependenceAliasName(name,pairParameter,paramTyps);
                for(ParameterValuePair valuePair:pairParameter.getPairs()){

                }
            }
            String value = getTemplateString(name,pairParameter);
            return value;
        }else{
            return transferValue(name);
        }
    }


    private static String getTemplateString(String value,PairParameter pairParameter) throws IOException, TemplateException {
        Configuration configuration = new Configuration();
        StringTemplateLoader loader = new StringTemplateLoader();
        loader.putTemplate(DEFAULT_TEMPLATE_NAME,value);
        configuration.setTemplateLoader(loader);
        configuration.setDefaultEncoding("UTF-8");
        Template template = configuration.getTemplate(DEFAULT_TEMPLATE_NAME);
        StringWriter stringWriter = new StringWriter();
        template.process(models,stringWriter);
        return stringWriter.toString();
    }


    private static String transferValue(String value){
        if("<empty>".equals(value)){
            return "";
        }else if("<null>".equals(value)){
            return null;
        }else{
            return value;
        }
    }


//    private static void checkDependenceAliasName(String objectValue,PairParameter pairParameter,Map<String,Class<?>>paramTyps) throws IOException, TemplateException {
//        String value = StringUtils.substringBetween(objectValue,"${","}");
//        List<String> aliasNames= Lists.newArrayList();
//        if(value.contains("(")&&value.contains(")")){
//            String[] strings = value.split(",");
//            for(String string:strings){
//                if(!string.contains("\"")&&paramTyps.containsKey(getAliasName(string))){
//                    aliasNames.add(string);
//                    if(!isContains(getAliasName(string))){
//                        createObject(getAliasName(string),pairParameter,paramTyps);
//                    }
//                }
//            }
//        }else{
//            String aliasName=
//        }
//    }


    private static String getAliasName(String value){
        if(value.contains(".")){
            return StringUtils.substringBefore(value,".");
        }else{
            return value;
        }
    }


//    private static void  createObject(String aliasName,PairParameter pairParameter,Map<String,Class<?>> paramTypes) throws IOException, TemplateException {
//        Object object;
//        Class<?> clazz=
//        try {
//            Constructor constructor = paramTypes.get(aliasName).getConstructor();
//            object = constructor.newInstance();
//        } catch (Exception e) {
//            throw new BeanClassNotMatchException("Bean.class注解只能应用于Bean方法里面,class构造方法有错误");
//        }
//        for(ParameterValuePair pair:pairParameter.getPairs()){
//            if(pair.getParameterName().startsWith(aliasName)){
//                String beanValue = org.apache.commons.lang3.StringUtils.substringAfter(pair.getParameterName(), ".");
//                BeanUtils.setProperty(object, beanValue, getValue(pair.getParameterValue(), pairParameter, paramTypes));
//            }
//        }
//        register(aliasName,object);
//    }





    private static boolean isMatchOtherParameter(String objectValue,PairParameter pairParameter){
        if(checkNameFormat(objectValue)){
            String value = StringUtils.substringBetween(objectValue,"${","}");
            if(value.contains(".")){
                value =StringUtils.substringBefore(value,".");
            }
            if(isContains(value)){
                return true;
            }else{
                if(pairParameter.isContainsAliasName(value)){
                    return true;
                }else{
                    return false;
                }
            }
        }
        return false;
    }

}

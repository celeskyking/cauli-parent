package org.cauli.mock.util;

import com.google.common.collect.Maps;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.cauli.mock.core.ValuePairs;

import java.io.StringWriter;
import java.util.Map;

/**
 * Created by tianqing.wang on 2014/6/19
 */
public class ValueTransfer {
    private final static String DEFAULT_TEMPLATE_NAME = "_default_name_";
    static Configuration configuration = new Configuration();

    public static Map<String,Object> models = Maps.newHashMap();



    public static void addModels(Map<String,Object> map){
        if(null!=map){
            models.putAll(map);
        }
    }
    private static boolean checkNameFormat(String name){
        if(name.contains("${")){
            return name.contains("}");
        }else if(name.contains("}")){
            return name.contains("${");
        }
        return false;
    }


    public static void register(String name,Object model){
        models.put(name,model);
    }


    public static String getValue(String name) throws Exception {
        if(checkNameFormat(name)){
            StringTemplateLoader loader = new StringTemplateLoader();
            loader.putTemplate(DEFAULT_TEMPLATE_NAME,name);
            configuration.setTemplateLoader(loader);
            configuration.setDefaultEncoding("UTF-8");
            Template template = configuration.getTemplate(DEFAULT_TEMPLATE_NAME);
            StringWriter stringWriter = new StringWriter();
            template.process(models,stringWriter);
            return stringWriter.toString();
        }else{
            return transferValue(name);
        }
    }


    public static String getValue(String name,ValuePairs valuePairs) throws Exception {
        if(checkNameFormat(name)){
            StringTemplateLoader loader = new StringTemplateLoader();
            loader.putTemplate(DEFAULT_TEMPLATE_NAME,name);
            configuration.setTemplateLoader(loader);
            configuration.setDefaultEncoding("UTF-8");
            Template template = configuration.getTemplate(DEFAULT_TEMPLATE_NAME);
            StringWriter stringWriter = new StringWriter();
            template.process(valuePairs.getValues(),stringWriter);
            return stringWriter.toString();
        }else{
            return transferValue(name);
        }
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
}

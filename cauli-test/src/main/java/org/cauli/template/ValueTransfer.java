package org.cauli.template;

import com.google.common.collect.Maps;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;
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


    public static String getValue(String name) throws IOException, TemplateException {
        if(checkNameFormat(name)){
            Configuration configuration = new Configuration();
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

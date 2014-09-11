package org.cauli.mock.util;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

/**
 * @auther sky
 */
public class TemplateParseUtil {

    private  static volatile  TemplateParseUtil templateParseUtil;
    private Configuration configuration;
    private StringTemplateLoader templateLoader;

    private TemplateParseUtil(){
        configuration = new Configuration();
        this.templateLoader = new StringTemplateLoader();
        configuration.setTemplateLoader(templateLoader);
        configuration.setDefaultEncoding("UTF-8");
    }

    public static TemplateParseUtil getInstance() {
        if(templateParseUtil==null){
            synchronized (TemplateUtil.class){
                if(templateParseUtil==null){
                    templateParseUtil=new TemplateParseUtil();
                }
            }
        }
        return templateParseUtil;
    }

    public Template getTemplate(String name) throws IOException {
        return configuration.getTemplate(name);
    }

    public String toString(Map map,String name) throws IOException, TemplateException {
        configuration.clearTemplateCache();
        StringWriter writer = new StringWriter();
        Template template = getTemplate(name);
        template.process(map,writer);
        return writer.toString();
    }


    public void addTemplate(String name,String templateValue){
        this.templateLoader.putTemplate(name,templateValue);
    }



}

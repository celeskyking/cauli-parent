package org.cauli.template;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * Created by tianqing.wang on 2014/9/11
 */
public class TemplateUtils {

    private final String DEFAULT_TEMPLATE_NAME="_default_name";
    private Configuration configuration;
    private StringTemplateLoader stringTemplateLoader;


    private static volatile TemplateUtils templateUtils;

    public static TemplateUtils getInstance(){
        if(templateUtils==null){
            synchronized (TemplateUtils.class){
                if(templateUtils==null){
                    templateUtils=new TemplateUtils();
                }
            }
        }
        return templateUtils;
    }

    private TemplateUtils(){
        this.configuration=new Configuration();
        this.stringTemplateLoader=new StringTemplateLoader();
        configuration.setDefaultEncoding("UTF-8");
        configuration.setTemplateLoader(stringTemplateLoader);
    }

    private void configTemplate(String template){
        this.stringTemplateLoader.putTemplate(DEFAULT_TEMPLATE_NAME,template);
    }


    public Template getTempalte(String template){
        configTemplate(template);
        try {
            return configuration.getTemplate(DEFAULT_TEMPLATE_NAME);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

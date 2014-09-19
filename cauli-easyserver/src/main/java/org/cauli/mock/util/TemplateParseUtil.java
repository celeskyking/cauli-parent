package org.cauli.mock.util;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;

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
    private final static String DEFAULT_NAME = "_default_name";

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

    public Template getTemplate() throws IOException {
        return configuration.getTemplate(DEFAULT_NAME);
    }

    public String toString(Map map,String content) throws IOException, TemplateException {
        if(StringUtils.isEmpty(content)){
            throw new RuntimeException("获取模板值为空");
        }
        this.templateLoader.putTemplate(DEFAULT_NAME,content);
        StringWriter writer = new StringWriter();
        Template template = getTemplate();
        template.process(map,writer);
        return writer.toString();
    }






}

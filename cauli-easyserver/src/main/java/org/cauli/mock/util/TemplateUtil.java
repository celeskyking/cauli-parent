package org.cauli.mock.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;
import org.cauli.instrument.ResourceUtil;

import java.util.Map;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

/**
 * Created by tianqing.wang on 2014/7/9
 */
public class TemplateUtil {

    private  static volatile  TemplateUtil templateUtil;
    private Configuration configuration;
    private File templateDir;

    TemplateUtil() throws IOException {
        configuration = new Configuration();
        this.templateDir = ResourceUtil.getFileFromClassPath("template");
        configuration.setDirectoryForTemplateLoading(templateDir);
    }

    public static TemplateUtil getInstance() throws IOException {
           if(templateUtil==null){
               synchronized (TemplateUtil.class){
                   if(templateUtil==null){
                       templateUtil=new TemplateUtil();
                   }
               }
           }
        return templateUtil;
    }

    public Template getTemplate(File templateFile,String encoding) throws IOException {
        return configuration.getTemplate(StringUtils.substring(templateFile.getAbsolutePath(),templateDir.getAbsolutePath().length()),encoding);
    }

    public String toString(Map map,File templateFile,String encoding) throws IOException, TemplateException {
        configuration.clearTemplateCache();
        StringWriter writer = new StringWriter();
        Template template = getTemplate(templateFile,encoding);
        template.process(map,writer);
        return writer.toString();
    }

    public String toString(Map map,File templateFile) throws IOException, TemplateException {
        StringWriter writer = new StringWriter();
        Template template = getTemplate(templateFile,"UTF-8");
        template.process(map,writer);
        return writer.toString();
    }





}

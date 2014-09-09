package org.cauli.server.controller;

import com.google.common.io.Resources;
import freemarker.template.*;
import jodd.io.FileUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by tianqing.wang on 2014/9/1
 */
public class FreeMarkerRender extends Render{

    private Logger logger = LoggerFactory.getLogger(FreeMarkerRender.class);
    private static final long serialVersionUID = -7649769283048920381L;
    private transient static final Configuration config = new Configuration();

    private static volatile FreeMarkerRender render;

    private FreeMarkerRender() throws IOException {
        init();
    }

    public static FreeMarkerRender getInstance() throws IOException {
        if(render==null){
            synchronized (FreeMarkerRender.class){
                if(render==null){
                    render=new FreeMarkerRender();
                }
            }
        }
        return render;
    }



    public void configViewPath(String viewPath) throws IOException {
        File file = new File(viewPath);
        if(file.exists()){
            logger.error("未找到该视图路径：{}",viewPath);
            throw new FileNotFoundException("未找到视图路径:"+viewPath);
        }else{
            config.setDirectoryForTemplateLoading(file);
        }
    }


    public void setView(String view) {
        this.view=view;
    }



    /**
     * freemarker can not load freemarker.properies automatically
     */
    public static Configuration getConfiguration() {
        return config;
    }

    /**
     * Set freemarker's property.
     * Example: FreeMarkerRender.setProperty("template_update_delay", "1600");
     */
    public static void setProperty(String propertyName, String propertyValue) {
        try {
            FreeMarkerRender.getConfiguration().setSetting(propertyName, propertyValue);
        } catch (TemplateException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setProperties(Properties properties) {
        try {
            FreeMarkerRender.getConfiguration().setSettings(properties);
        } catch (TemplateException e) {
            throw new RuntimeException(e);
        }
    }

    void init() throws IOException {

        config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        config.setObjectWrapper(ObjectWrapper.BEANS_WRAPPER);
        config.setDefaultEncoding("UTF-8");		// config.setDefaultEncoding("ISO-8859-1");		// config.setOutputEncoding("UTF-8");
        config.setLocalizedLookup(false);
        config.setNumberFormat("#0.#####");
        config.setDateFormat("yyyy-MM-dd");
        config.setTimeFormat("HH:mm:ss");
        config.setDateTimeFormat("yyyy-MM-dd HH:mm:ss");
        config.setTemplateUpdateDelay(0);
        try{
            config.setDirectoryForTemplateLoading(new File(Resources.getResource("views").getFile()));
        }catch (Exception e){
            logger.debug("未配置视图路径:classpath:views");
        }


    }

    public void render() throws IOException {

        Map root = new HashMap();
        for(String key:request.dataKeys()){
            root.put(key,request.data(key));
        }
        StringWriter writer = new StringWriter();
        try {
            Template template = config.getTemplate(view);
            template.process(root, writer);
            response.content(writer.toString()).end();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            if (writer != null)
                writer.close();
        }
    }
}

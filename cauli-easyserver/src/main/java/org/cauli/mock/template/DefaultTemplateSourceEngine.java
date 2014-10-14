package org.cauli.mock.template;

import com.google.common.collect.Maps;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.cauli.common.instrument.ResourceUtil;
import org.cauli.mock.action.MockAction;
import org.cauli.mock.util.TemplateParseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * @auther sky
 */
public class DefaultTemplateSourceEngine implements TemplateSourceEngine{

    private Logger logger = LoggerFactory.getLogger(DefaultTemplateSourceEngine.class);

    private MockAction action;

    private Map<String,String> templateCache = Maps.newHashMap();

    public DefaultTemplateSourceEngine(MockAction action){
        this.action=action;
    }


    @Override
    public Set<String> getReturnStatuses() {
        return templateCache.keySet();
    }

    @Override
    public void createTemplate(String status, String content) {
        templateCache.put(status,content);
    }

    @Override
    public void init(){
        File file = ResourceUtil.getFileFromClassPath("template");
        for(File f:file.listFiles()){
            if(f.isDirectory()&&f.getName().equals(action.getServer().getServerName())){
                for(File ff:f.listFiles()){
                    String name = ff.getName();
                    if(name.equals(action.getActionName())&&ff.isDirectory()){
                        for(File fff:ff.listFiles()){
                            String content;
                            try{
                                content = IOUtils.toString(new FileInputStream(fff));
                            }catch (Exception e){
                                throw new RuntimeException("读取"+ff.getName()+"的流失败",e);
                            }
                            if(!fff.getName().contains("_callback")){
                                String returnStatus = StringUtils.substringBefore(fff.getName(),".");
                                templateCache.put(returnStatus,content);
                            }else{
                                String returnStatus = StringUtils.substringBefore(fff.getName(), ".");
                                templateCache.put(returnStatus,content);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean hasReturnStatus(String returnStatus) {
        return templateCache.containsKey(returnStatus);
    }

    @Override
    public String getTemplate(String status) {
        return templateCache.get(status);
    }

    @Override
    public void updateTemplate(String status, String templateValue) {
        if(templateCache.containsKey(status)){
            templateCache.remove(status);
        }
        templateCache.put(status,templateValue);
    }

    @Override
    public void deleteTemplate(String status) {
        templateCache.remove(status);
    }

    @Override
    public Map<String,String> getActionTemplates() {
        return templateCache;
    }

    @Override
    public Map<String, String> getCallbackTemplates() {
        Map<String,String> callbackCache = Maps.newHashMap();
        for(Map.Entry<String,String> entry:templateCache.entrySet()){
            if(entry.getKey().contains("_callback")){
                String status = StringUtils.substringBefore(entry.getKey(), "_");
                logger.info("获取Action[{}]的Callback的模板,状态为:{},内容为:{}",action.getActionName(),status,entry.getValue());
                callbackCache.put(status,entry.getValue());
            }
        }
        return callbackCache;
    }
}

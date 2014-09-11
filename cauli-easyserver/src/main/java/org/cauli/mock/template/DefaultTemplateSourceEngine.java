package org.cauli.mock.template;

import com.google.common.collect.Maps;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.cauli.mock.action.MockAction;
import org.cauli.instrument.ResourceUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * @auther sky
 */
public class DefaultTemplateSourceEngine implements TemplateSourceEngine{

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
                    if(ff.getName().startsWith(action.getActionName())){
                        String content = null;
                        try {
                            content = IOUtils.toString(new FileInputStream(ff));
                        } catch (IOException e) {
                            throw new RuntimeException("读取"+ff.getName()+"的流失败");
                        }
                        templateCache.put(StringUtils.substringBetween(ff.getName(), "_", "."), content);
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
    public Map<String,String> getAllTemplates() {
        return templateCache;
    }
}

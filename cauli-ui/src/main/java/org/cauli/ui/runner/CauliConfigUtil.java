package org.cauli.ui.runner;

import com.google.common.collect.Maps;
import org.cauli.junit.PropertiesTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.util.Map;
import java.util.Properties;

/**
 * Created by tianqing.wang on 14-3-31
 */
public class CauliConfigUtil {
    private Map<String,String> configs;
    private Logger logger = LoggerFactory.getLogger(CauliConfigUtil.class);
    private static CauliConfigUtil self;
    private CauliConfigUtil(){
        this.configs= getConfigs();
    }

    public static CauliConfigUtil getInstance(){
        if(self==null){
            synchronized (CauliConfigUtil.class){
                if(self==null){
                    self=new CauliConfigUtil();
                }
            }
        }
        return self;
    }

    private Map<String,String> getConfigs() {
        try{
            Map<String,String> configs = Maps.newHashMap();
            ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource[] resources=null;
            try{
                resources = resolver.getResources("classpath:config/config.properties");
            }catch(Exception e){
                logger.warn("没有获取到配置文件...不适用配置模式..");
                return Maps.newHashMap();
            }
            for(Resource resource:resources){
                Properties properties = new Properties();
                properties.load(resource.getInputStream());
                configs.putAll(PropertiesTools.getPropertiesMap(properties));
            }
            return configs;
        }catch (Exception e){
            throw new RuntimeException("加载配置文件的时候出现了错误...",e);
        }

    }

    public String get(String key){
        return this.configs.get(key);
    }

}

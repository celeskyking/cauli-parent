package org.cauli.config;

import com.google.common.io.Resources;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Properties;

/**
 * Created by tianqing.wang on 2014/5/7
 */
public class ResourceConfig {

    public Map<String,String> getConfigProperties() throws IOException {
        URL url = Resources.getResource("config.properties");
        if(url==null){
            return DefaultConfig.getConfig();
        }else{
            Properties properties = new Properties();
            properties.load(url.openStream());
        }
        return null;
    }
}

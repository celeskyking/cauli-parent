package org.cauli.mock.util;


import org.cauli.junit.PropertiesTools;

import java.util.Properties;

/**
 * Created by tianqing.wang on 2014/7/15
 */
public class ConfigUtil {


    public static Properties parse(){
        return PropertiesTools.getPropertiesFromClassPath("mock.properties");
    }


    public static String getSignUrl(){
        return (String) parse().get("sign_url");
    }
}

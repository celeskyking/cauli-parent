package org.cauli.mock.util;


import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

import java.util.Map;
import java.util.Properties;

/**
 * Created by tianqing.wang on 2014/9/17
 */
public class UrlKeyValueUtil {
    private static Joiner.MapJoiner mapJoiner = Joiner.on("&").withKeyValueSeparator("=");
    private static Splitter.MapSplitter splitter = Splitter.on("&").withKeyValueSeparator("=");
    public static String join(Map<String,String> map){
        return mapJoiner.join(map);
    }

    public static String join(Properties properties){
        return mapJoiner.join(properties);
    }

    public static Map<String,String> getKeyValues(String url){
        return splitter.split(url);
    }

    public static String getValue(String url,String key){
        return getKeyValues(url).get(key);
    }


}

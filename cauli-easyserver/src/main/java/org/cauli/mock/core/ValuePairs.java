package org.cauli.mock.core;

import com.google.common.collect.Maps;
import freemarker.template.TemplateMethodModelEx;

import java.util.Map;

/**
 * Created by tianqing.wang on 2014/7/9
 */
public class ValuePairs {

    private Map<String,Object> values= Maps.newHashMap();

    public void addObject(String key,Object object){
        values.put(key,object);
    }

    public void addObjects(Map<String,Object> map){
        values.putAll(map);
    }

    public Map<String, Object> getValues() {
        return values;
    }


    public void addTemplateMethodModelObjects(Map<String, TemplateMethodModelEx> map){
        values.putAll(map);
    }
}

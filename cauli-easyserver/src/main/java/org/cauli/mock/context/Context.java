package org.cauli.mock.context;

import com.google.common.collect.Maps;
import freemarker.template.TemplateMethodModelEx;

import java.util.Map;

/**
 * Created by tianqing.wang on 2014/7/9
 */
public class Context {

    private Map<String,Object> contextMap = Maps.newHashMap();

    private Context parent;

    public void addContext(String name,Object object){
        this.contextMap.put(name,object);
    }

    public void addContext(Map<String, Object> context) {
        this.contextMap.putAll(context);
    }

    public void addObject(String name,Object object){
        addContext(name,object);
    }

    public Object getContext(String key) {
        Object value = this.contextMap.get(key);
        Context parent = getParent();
        if (value == null && parent != null) {
            value = parent.getContext(key);
        }
        return value;
    }


    public String getString(String key){
        return String.valueOf(getContext(key));
    }

    public int getInt(String key){
        return Integer.valueOf(getString(key));
    }

    public double getDouble(String key){
        return Integer.valueOf(getString(key));
    }


    public float getFloat(String key){
        return Float.valueOf(getString(key));
    }

    public long getLong(String key){
        return Long.valueOf(getString(key));
    }

    public Context getParent() {
        return parent;
    }

    public void setParent(Context parent) {
        this.parent = parent;
    }

    public Map<String, Object> getValues() {
        Map<String,Object> values = Maps.newHashMap();
        Context context=this;
        while (context!=null){
            for(String key : context.getContextMap().keySet()){
                if(!values.containsKey(key)){
                    values.put(key,context.getContext(key));
                }
            }
            context=context.getParent();
        }
        return values;
    }

    public Map<String,Object> getContextMap(){
        return contextMap;
    }


    public void addTemplateMethodModelObjects(Map<String, TemplateMethodModelEx> map){
        contextMap.putAll(map);
    }

}

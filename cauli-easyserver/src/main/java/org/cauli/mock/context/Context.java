package org.cauli.mock.context;

import com.google.common.collect.Maps;
import java.util.Map;

/**
 * Created by tianqing.wang on 2014/9/19
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

    public Object getContext(String key) {
        Object value = this.contextMap.get(key);
        Context parent = getParent();
        if (value == null && parent != null) {
            value = parent.getContext(key);
        }
        return value;
    }

    public Context getParent() {
        return parent;
    }

    public void setParent(Context parent) {
        this.parent = parent;
    }
}

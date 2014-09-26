package org.cauli.common.keyvalue;

/**
 * Created by tianqing.wang on 2014/9/17
 */
public class KeyValueStore {

    private String key;
    private Object value;

    public KeyValueStore(String key,Object value){
        this.key=key;
        this.value=value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getString(){
        return String.valueOf(value);
    }

    public Integer getInt(){
        return Integer.parseInt(value.toString());
    }

    public Boolean getBoolean(){
        return Boolean.valueOf(value.toString());
    }

    public Float getFloat(){
        return Float.valueOf(value.toString());
    }

    public Long getLong(){
        return Long.valueOf(value.toString());
    }

}

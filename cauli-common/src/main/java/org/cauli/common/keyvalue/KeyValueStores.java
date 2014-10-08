package org.cauli.common.keyvalue;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Joiner;
import com.google.common.collect.Maps;

import java.util.*;

/**
 * Created by tianqing.wang on 2014/9/19
 */
public class KeyValueStores {

    private PriorityQueue<KeyValueStore> queue;

    private ValueHandler handler;

    public KeyValueStores(){
        this.queue=new PriorityQueue<KeyValueStore>(30,new NormalSortComparator());
        this.handler=new ValueHandler() {
            @Override
            public Object transfer(Object value) {
                return value;
            }
        };
    }

    public KeyValueStores(Comparator<KeyValueStore> comparator,ValueHandler valueHandler){
        this(comparator);
        this.handler=valueHandler;
    }

    public KeyValueStores(ValueHandler valueHandler){
        this();
        this.handler=valueHandler;
    }

    public KeyValueStores(Comparator<KeyValueStore> comparator){
        this.queue=new PriorityQueue<KeyValueStore>(30,comparator);
        this.handler=new ValueHandler() {
            @Override
            public Object transfer(Object value) {
                return value;
            }
        };
    }

    public KeyValueStore getKeyStore(String key){
        Iterator<KeyValueStore> iterator = queue.iterator();
        while(iterator.hasNext()){
            KeyValueStore keyValueStore = iterator.next();
            if(keyValueStore.getKey().equals(key)){
                return keyValueStore;
            }
        }
        return null;
    }

    public Object getObject(String key){
        return getKeyStore(key).getValue();
    }

    public void add(KeyValueStore keyValueStore){
        KeyValueStore store = new KeyValueStore(keyValueStore.getKey(),handler.transfer(keyValueStore.getValue()));
        this.queue.add(store);
    }

    public Map<String,Object> toMap(){
        LinkedHashMap<String,Object> map = Maps.newLinkedHashMap();
        Queue<KeyValueStore> queue1 = queue;
        while(!queue1.isEmpty()){
            KeyValueStore keyValueStore = queue1.poll();
            map.put(keyValueStore.getKey(),keyValueStore.getValue());
        }
        return map;
    }

    public boolean remove(String key){
        Iterator<KeyValueStore> iterator = queue.iterator();
        while(iterator.hasNext()){
            KeyValueStore keyValueStore = iterator.next();
            if(keyValueStore.getKey().equals(key)){
                return queue.remove(keyValueStore);
            }
        }
        return false;
    }

    public String toString(){
        Joiner.MapJoiner joiner = Joiner.on("&").withKeyValueSeparator("=");
        return joiner.join(toMap());

    }

    public Iterator<KeyValueStore> iterator(){
        return this.queue.iterator();
    }


    public void add(KeyValueStores stores){
        Iterator<KeyValueStore> iterator = stores.iterator();
        while(iterator.hasNext()){
            KeyValueStore store = iterator.next();
            this.queue.add(new KeyValueStore(store.getKey(),handler.transfer(store.getValue())));
        }
    }

    public String toJSON(){
        return JSON.toJSONString(toMap());
    }


}

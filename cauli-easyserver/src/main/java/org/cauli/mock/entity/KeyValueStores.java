package org.cauli.mock.entity;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;

import java.util.*;

/**
 * Created by tianqing.wang on 2014/9/19
 */
public class KeyValueStores {

    private PriorityQueue<KeyValueStore> queue;

    public KeyValueStores(){
        this.queue=new PriorityQueue<KeyValueStore>(30,new Comparator<KeyValueStore>() {
            @Override
            public int compare(KeyValueStore o1, KeyValueStore o2) {
                return o1.getKey().compareTo(o2.getKey());
            }
        });
    }

    public KeyValueStores(Comparator<KeyValueStore> comparator){
        this.queue=new PriorityQueue<KeyValueStore>(30,comparator);
    }

    public void add(KeyValueStore keyValueStore){
        this.queue.add(keyValueStore);
    }

    public Map<String,Object> toMap(){
        LinkedHashMap<String,Object> map = Maps.newLinkedHashMap();
        Iterator<KeyValueStore> iterator = queue.iterator();
        while(iterator.hasNext()) {
            KeyValueStore keyValueStore = iterator.next();
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

}

package org.cauli.mock;

import com.google.common.collect.Lists;
import org.cauli.common.keyvalue.KeyValueStore;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by tianqing.wang on 2014/10/20
 */
public class FixedSizeBox<T>{

    private LinkedList<KeyValueStore>linkedList = Lists.newLinkedList();

    public final static int DEFAULT_SIZE = 10;

    public void add(KeyValueStore keyValueStore){
        if(this.linkedList.size()>=DEFAULT_SIZE){
            this.linkedList.removeFirst();
            this.linkedList.add(keyValueStore);
        }else{
            this.linkedList.add(keyValueStore);
        }
    }

    public void first(){
        this.linkedList.peek();
    }

    public void last(){
        this.linkedList.peekFirst();
    }

    public T get(String key){
        for(KeyValueStore keyValueStore :linkedList){
            if(keyValueStore.getKey().equals(key)){
                return (T) keyValueStore.getValue();
            }
        }
        return null;
    }

    /**排序顺序是从最近到最早*/
    public List<String> getKeys(){
        List<String> keys = Lists.newArrayList();
        for(KeyValueStore keyValueStore:linkedList){
            keys.add(keyValueStore.getKey());
        }
        return keys;
    }


}

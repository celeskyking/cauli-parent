package org.cauli.db;

import com.google.common.collect.Maps;

import java.util.Map;
/**
 * Created by tianqing.wang on 14-3-21
 */
public class DbManager {

    public static Map<String,DBCore> dbs= Maps.newHashMap();

    private DbManager(){};

    public static void register(String name,DBCore dbCore){
        dbs.put(name,dbCore);
    }

    public static DBCore getDBCore(String name){
        return dbs.get(name);
    }

    public void remove(String name){
         dbs.remove(name);
    }

}

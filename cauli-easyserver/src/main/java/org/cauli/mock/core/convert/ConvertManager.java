package org.cauli.mock.core.convert;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @auther sky
 */
public class ConvertManager {

    private final static Map<Class<?>,ConvertExecuter> converterMap = Maps.newHashMap();

    public static  void register(Class<?> clazz,ConvertExecuter converter){
        converterMap.put(clazz, converter);
    }


    public static ConvertExecuter getGeneratorConverter(Class<?> clazz){
        return converterMap.get(clazz);
    }


    public static Map<Class<?>,ConvertExecuter> getConverterMap(){
        return converterMap;
    }


    public static class ConvertMap{
        private Map<Class<?>,ConvertExecuter> map = Maps.newHashMap();
        public ConvertMap(){
            map.putAll(ConvertManager.getConverterMap());
        }

        public Map<Class<?>,ConvertExecuter> getMap(){
            return map;
        }

        public void register(Class<?> clazz,ConvertExecuter converter){
            map.put(clazz,converter);
        }

        public  ConvertExecuter getConvertExecuter(Class<?> key){
            return map.get(key);
        }


        public boolean contains(Class<?> clazz){
            return map.containsKey(clazz);
        }

        public void dump(){
            for(Map.Entry<Class<?>,ConvertExecuter>entry:map.entrySet()){
                System.out.println("key:"+entry.getKey().getName()+" value:"+entry.getValue().getClass().getName());
            }
        }
    }

}

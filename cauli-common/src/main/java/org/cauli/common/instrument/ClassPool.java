package org.cauli.common.instrument;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * 本地class文件的扫描方法，初始化只能扫描一次，不能够重复扫描，如果需要重置
 * */
public class ClassPool {

	/**
	 *@return 返回项目目录下的class的集合。
	 */
	public static Set<Class<?>> getClassPool(){
        Set<Class<?>> classPool;
        ClassFinder classPathScanHandler = new ClassFinder(false, true, null);
        classPool=classPathScanHandler.getPackageAllClasses("", true);
        return classPool;
	}
    /**
     * @return 返回类集合的map形式，key值为类名，不包含包名
     * */
    public static Map<String,Class<?>> getClassPair(){
        Map<String,Class<?>> classMap = new HashMap<String, Class<?>>();
        for(Class<?>clazz:getClassPool()){
            classMap.put(clazz.getSimpleName(),clazz);
        }
        return classMap;

    }

    public static Class<?> getClassByClassSimpleName(String name){
        return getClassPair().get(name);
    }
    /**
     *@return 返回项目目录下的基础包下的class的集合。
     */
    public static Set<Class<?>> getClassPool(String baseName){
        Set<Class<?>> classPool;
        ClassFinder classPathScanHandler = new ClassFinder(false, true, null);
        classPool=classPathScanHandler.getPackageAllClasses(baseName, true);
        return classPool;
    }



    /**
     * 获取项目中某一个类的所有子类
     * */
    public static Set<Class<?>> getChildClasses(Class<?>parent){
        Set<Class<?>> classes = new HashSet<Class<?>>();
        for(Class<?> childclass:getClassPool()){
            if(ClassUtils.isAssignableFromSubClass(parent, childclass)){
                classes.add(childclass);
            }
        }
        return classes;
    }

}

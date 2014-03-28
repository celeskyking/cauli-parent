package org.cauli.instrument;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * 本地class文件的扫描方法，初始化只能扫描一次，不能够重复扫描，如果需要重置
 * */
public class ClassPool {
	
	static private Set<Class<?>> classPool=new HashSet<Class<?>>();
    static private Map<String,Class<?>> classMap = new HashMap<String, Class<?>>();
	/**
	 *@return 返回项目目录下的class的集合。
	 */
	public static Set<Class<?>> getClassPool(){
		if(classPool.size()==0){
			ClassFinder classPathScanHandler = new ClassFinder(false, true, null);
			classPool=classPathScanHandler.getPackageAllClasses("", true);
			return classPool;
		}else{
			return classPool;
		}
	}
    /**
     * @return 返回类集合的map形式，key值为类名，不包含包名
     * */
    public static Map<String,Class<?>> getClasses(){
        if(classMap.size()==0){
            ClassFinder finder = new ClassFinder(false,true,null);
            for(Class<?>clazz:getClassPool()){
                classMap.put(clazz.getSimpleName(),clazz);
            }
            return classMap;
        }else{
            return classMap;
        }
    }

    public static Class<?> getClass(String name){
        return getClasses().get(name);
    }
    /**
     *@return 返回项目目录下的基础包下的class的集合。
     */
    public static Set<Class<?>> getClassPool(String baseName){
        if(classPool.size()==0){
            ClassFinder classPathScanHandler = new ClassFinder(false, true, null);
            classPool=classPathScanHandler.getPackageAllClasses(baseName, true);
            return classPool;
        }else{
            return classPool;
        }
    }



	/**
	 *@param clazz 需要添加的class对象，需要在classpath找到的class
	 */
	public static void addClass(Class<?> clazz){
		classPool.add(clazz);
	}
	
	
	public static void reset(){
		classPool.clear();
	}
	
	
	public static void removeClass(Class<?> clazz){
		classPool.remove(clazz);
	}

    public static void main(String[] args){
        Set<Class<?>> classes = ClassPool.getClassPool("org.junit");
        for(Class clazz:classes){
            System.out.println(clazz.getName());
        }
    }
    /**
     * 获取项目中某一个类的所有子类
     * */
    public static Set<Class<?>> getChildClasses(Class<?>clazz){
        Set<Class<?>> classes = new HashSet<Class<?>>();
        for(Class<?> childclass:getClassPool()){
            if(clazz.isAssignableFrom(childclass)){
                classes.add(childclass);
            }
        }
        return classes;
    }
}

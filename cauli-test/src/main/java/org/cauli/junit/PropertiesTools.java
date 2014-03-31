package org.cauli.junit;

import java.io.*;
import java.util.*;


/**操作Properties文件的工具类
 * */
public class PropertiesTools {
	
	/**
	 * 获取指定位置的property文件中property文件信息
	 * @param fullFilePath property文件的全路径信息
	 * @return 属性文件的信息
	 * */
	public static Properties getPropertyInfo(String fullFilePath){
		File propertyFile=new File(fullFilePath);
		return getPropertyInfo(propertyFile);
	}
	/**
	 * 获取指定文件对象对应的property文件的信息
	 * @param propertyFile property文件
	 * @return 属性文件的信息
	 * */
	public static Properties getPropertyInfo(File propertyFile){
		if(!propertyFile.exists()){
			System.out.println("配置文件没有找到！");
		}
		try {
			FileInputStream inputStream=new FileInputStream(propertyFile);
			Properties properties=new Properties();
			properties.load(inputStream);
			return properties;
		} catch (FileNotFoundException e) {
			System.out.println("属性文件不存在，请检查!");
		} catch (IOException e) {
			System.out.println("Property.load 的时候发生io错误!");
		}
		return null;
	}
	/**
	 * 将properties对象写入到fullPath指定的路径中的文件中去,如果已经存在进行更新
	 * @param properties property对象
	 * @param fullPath 指定的文件地址,带有后缀".properties"的
	 * */
	public static void savePropertiesFile(Properties properties,String fullPath,String comments){
		MyFile.createFile(fullPath);
		try {
			FileOutputStream outputStream=new FileOutputStream(new File(fullPath));
			properties.store(outputStream, comments);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**获得properties文件里面指定属性名字的属性值，如果不存在该属性或者该属性的值为""，抛出异常
	 * @param properties 给定的properties文件
	 * @param propertiesName 属性名字
	 * @return 属性值
	 *  */
	public static String getValue(Properties properties,String propertiesName){
		String value=properties.getProperty(propertiesName);
		if(value==null||"".equalsIgnoreCase(value)){
			System.out.println("获取的属性值不存在或者为空窜!");
		}return value;
	}
	/**获得properties文件里面指定属性名字的属性值，如果不存在该属性或者该属性的值为""，返回默认值
	 * @param properties 给定的properties文件
	 * @param propertiesName 属性名字
	 * @param defaultValue 在属性值不存在或者为""的时候的返回的默认值
	 * @return 属性值
	 *  */
	public static String getValue(Properties properties,String propertiesName,String defaultValue){
		String value=properties.getProperty(propertiesName);
		if(value==null||"".equalsIgnoreCase(value)){
			return defaultValue;
		}return value;
	}
	/**通常在一个properties文件里面一个属性会有很多值，他们之间用','隔开，那么这个方法的作用就是获得所有的值<br>
	 * 属性值为""的不加入返回的列表，如果属性值不存在或者所有的值都为"",抛出异常
	 * @param properties 给定的properties文件
	 * @param propertiesName 属性名字
	 * @return 属性值列表*/
	public static List<String>getValueList(Properties properties,String propertiesName){
		ArrayList<String>valuesList;
		String values=getValue(properties, propertiesName);
		valuesList=new ArrayList<String>(values.length());
		String[]propertieValues=values.split(",");
		for(String value:propertieValues){
			if(value.trim().equalsIgnoreCase(""))
				continue;
			valuesList.add(value.trim());
		}
		if(valuesList.isEmpty()){
			System.out.println("获取的属性列表所有的属性都为空窜!");
		}return valuesList;
	}
	/**判断指定名字的属性在属性文件里面是否存在，不管他的值如何，只要存在该属性，值为空窜也返回true
	 * @param properties 给定的properties文件
	 * @param propertiesName 属性名字
	 * @return true 存在该属性<false>不存在该属性
	 * */
	public static boolean isPropertyInFile(Properties properties,String propertiesName){
		return properties.get(propertiesName)==null?false:true;
	}
	/**获取boolean类型的属性值，也就是说该属性的值在properties文件的表现形式为"true"和"false"<br>
	 * 如果非此表现形式，抛出异常;也就是说此属性只能有一个值，如果多个值的话，该方法会抛出异常
	 * @param properties 给定的properties文件
	 * @param propertiesName 属性名字
	 * @return 属性值转换后的boolean形式*/
	public static boolean getBoolean(Properties properties,String propertiesName){
		String value=getValue(properties, propertiesName);
		if(value.trim().equalsIgnoreCase("true")){
			return true;
		}else if(value.trim().equalsIgnoreCase("false")){
			return false;
		}else{
			System.out.println("属性值的格式错误!无法转换为boolean类型!");
		}
		return false;
	}
	/**获取boolean类型的属性值，也就是说该属性的值在properties文件的表现形式为"true"和"false"<br>
	 * 如果非此表现形式，捕获异常，返回默认值;也就是说此属性只能有一个值，如果多个值的话，该方法会返回默认值
	 * @param properties 给定的properties文件
	 * @param propertiesName 属性名字
	 * @param defaultValue 捕获异常后返回的默认值
	 * @return 属性值转换后的boolean形式*/
	public static boolean getBoolean(Properties properties,String propertiesName,boolean defaultValue){
		try{
			return getBoolean(properties, propertiesName);
		}catch (Exception e) {
			return defaultValue;
		}
	}
	/**获取long类型的属性值，也就是说该属性的值在properties文件的表现形式为"ddd"(d代表数字，只支持10进制)<br>
	 * 如果非此表现形式，抛出异常;也就是说此属性只能有一个值，如果多个值的话，该方法会抛出异常
	 * @param properties 给定的properties文件
	 * @param propertiesName 属性名字
	 * @return 属性值转换后的long形式*/
	public static long getLong(Properties properties,String propertiesName){
		String value=getValue(properties, propertiesName);
		try{
			return Long.valueOf(value);
		}catch (NumberFormatException e) {
			System.out.println("获得的属性不是Long类型!无法完成转型!");
		}
		return 0;
	}
	/**获取long类型的属性值，也就是说该属性的值在properties文件的表现形式为"ddd"(d代表数字，只支持10进制)<br>
	 * 如果非此表现形式，捕获异常，返回默认值;也就是说此属性只能有一个值，如果多个值的话，该方法会返回默认值
	 * @param properties 给定的properties文件
	 * @param propertiesName 属性名字
	 * @param defaultValue 捕获异常后返回的默认值
	 * @return 属性值转换后的long形式*/
	public static long getLong(Properties properties,String propertiesName,long defaultValue){
		try{
			return getLong(properties, propertiesName);
		}catch (Exception e) {
			return defaultValue;
		}
	}
	/**获取int类型的属性值，也就是说该属性的值在properties文件的表现形式为"ddd"(d代表数字，只支持10进制)<br>
	 * 如果非此表现形式，抛出异常;也就是说此属性只能有一个值，如果多个值的话，该方法会抛出异常
	 * @param properties 给定的properties文件
	 * @param propertiesName 属性名字
	 * @return 属性值转换后的int形式*/
	public static int getInt(Properties properties,String propertiesName){
		String value=getValue(properties, propertiesName);
		try{
			return Integer.valueOf(value);
		}catch (NumberFormatException e) {
			System.out.println("获得的属性不是Long类型!无法完成转型!");
		}
		return 0;
	}
	/**获取int类型的属性值，也就是说该属性的值在properties文件的表现形式为"ddd"(d代表数字，只支持10进制)<br>
	 * 如果非此表现形式，捕获异常，返回默认值;也就是说此属性只能有一个值，如果多个值的话，该方法会返回默认值
	 * @param properties 给定的properties文件
	 * @param propertiesName 属性名字
	 * @param defaultValue 捕获异常后返回的默认值
	 * @return 属性值转换后的int形式*/
	public static int getInt(Properties properties,String propertiesName,int defaultValue){
		try{
			return getInt(properties, propertiesName);
		}catch (Exception e) {
			return defaultValue;
		}
	}
	/**当一个属性有多个值的时候，返回所有值的列表，这些值必须都为"true"或者"false"形式，如果有一个不是，抛出异常
	 * @param properties 给定的properties文件
	 * @param propertiesName 属性名字
	 * @return 属性值转换后的boolean列表形式*/
	public static List<Boolean> getBooleanList(Properties properties,String propertiesName){
		ArrayList<String> values=(ArrayList<String>) getValueList(properties, propertiesName);
		List<Boolean>booleans=new ArrayList<Boolean>();
		for(String value:values){
			if(value.trim().equalsIgnoreCase("true")){
				booleans.add(true);
			}else if(value.trim().equalsIgnoreCase("false")){
				booleans.add(false);
			}else{
				System.out.println("属性值的格式错误!无法转换为boolean类型!");
			}
		}
		return booleans;
	}
	/**当一个属性有多个值的时候，返回所有值的列表，这些值必须都为"true"或者"false"形式，如果有一个不是，那么这个值用defaultValue替换
	 * @param properties 给定的properties文件
	 * @param propertiesName 属性名字
	 * @param defaultValue 值不是boolean类型的时候用以替换的值
	 * @return 属性值转换后的boolean列表形式*/
	public static List<Boolean> getBooleanList(Properties properties,String propertiesName,boolean defaultValue){
		ArrayList<String> values=(ArrayList<String>) getValueList(properties, propertiesName);
		List<Boolean>booleans=new ArrayList<Boolean>();
		for(String value:values){
			if(value.trim().equalsIgnoreCase("true")){
				booleans.add(true);
			}else if(value.trim().equalsIgnoreCase("false")){
				booleans.add(false);
			}else{
				booleans.add(defaultValue);
			}
		}
		return booleans;
	}
	
	/**当一个属性有多个值的时候，返回所有值的列表，这些值必须都为"ddd"(只支持十进制)形式，如果有一个不是，抛出异常
	 * @param properties 给定的properties文件
	 * @param propertiesName 属性名字
	 * @return 属性值转换后的Long列表形式*/
	public static List<Long> getLongList(Properties properties,String propertiesName){
		ArrayList<String> values=(ArrayList<String>) getValueList(properties, propertiesName);
		List<Long>longs=new ArrayList<Long>();
		for(String value:values){
			try{
				longs.add(Long.valueOf(value));
			}catch (NumberFormatException e) {
				System.out.println("获得的属性不是Long类型!无法完成转型!");
			}
		}
		return longs;
	}
	/**当一个属性有多个值的时候，返回所有值的列表，这些值必须都为"ddd"(只支持十进制)形式，如果有一个不是，那么这个值用defaultValue替换
	 * @param properties 给定的properties文件
	 * @param propertiesName 属性名字
	 * @param defaultValue 值不是Long类型的时候用以替换的值
	 * @return 属性值转换后的Long列表形式*/
	public static List<Long> getLongList(Properties properties,String propertiesName,long defaultValue){
		ArrayList<String> values=(ArrayList<String>) getValueList(properties, propertiesName);
		List<Long>longs=new ArrayList<Long>();
		for(String value:values){
			try{
				longs.add(Long.valueOf(value));
			}catch (NumberFormatException e) {
				longs.add(defaultValue);
			}
		}
		return longs;
	}
	
	/**当一个属性有多个值的时候，返回所有值的列表，这些值必须都为"ddd"(只支持十进制)形式，如果有一个不是，抛出异常
	 * @param properties 给定的properties文件
	 * @param propertiesName 属性名字
	 * @return 属性值转换后的Int列表形式*/
	public static List<Integer> getIntList(Properties properties,String propertiesName){
		ArrayList<String> values=(ArrayList<String>) getValueList(properties, propertiesName);
		List<Integer>ints=new ArrayList<Integer>();
		for(String value:values){
			try{
				ints.add(Integer.valueOf(value));
			}catch (NumberFormatException e) {
				System.out.println("获得的属性不是Long类型!无法完成转型!");
			}
		}
		return ints;
	}
	/**当一个属性有多个值的时候，返回所有值的列表，这些值必须都为"ddd"(只支持十进制)形式，如果有一个不是，那么这个值用defaultValue替换
	 * @param properties 给定的properties文件
	 * @param propertiesName 属性名字
	 * @param defaultValue 值不是Int类型的时候用以替换的值
	 * @return 属性值转换后的Int列表形式*/
	public static List<Integer> getIntList(Properties properties,String propertiesName,int defaultValue){
		ArrayList<String> values=(ArrayList<String>) getValueList(properties, propertiesName);
		List<Integer>ints=new ArrayList<Integer>();
		for(String value:values){
			try{
				ints.add(Integer.valueOf(value));
			}catch (NumberFormatException e) {
				ints.add(defaultValue);
			}
		}
		return ints;
	}
	/**从用户的主目录获得sk配置文件
	 * @param propertiesFileName
	 * @return 找到的由properties配置文件创建的{@link Properties}对象，如果没有找到配置文件，返回null
	 * */
	public static Properties getPropertiesFromUserhome(String propertiesFileName){
		if(propertiesFileName==null||"".equalsIgnoreCase(propertiesFileName)){
			System.out.println("没有给定 properties文件的名字!");
		}
		InputStream input=null;
		Properties properties=new Properties();
		try{
			File userHomeProperties=new File(System.getProperty("user.home")+propertiesFileName);
			if(!userHomeProperties.exists()){
				return null;
			}
			input=new FileInputStream(userHomeProperties);
			properties.load(input);
			//System.out.println("位于用户主目录的配置文件加载成功!");
			return properties;
		}catch(IOException e){
			//System.out.println("从用户主目录加载配置文件失败，可能是文件不存在，或者发生其他IO错误!");
		}finally{
			try {
				input.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return properties;
	}
	/**从类路径获得配置文件(类路径下的Resource目录下)
	 * @param propertiesFileName
	 * @return 找到的由properties配置文件创建的{@link Properties}对象，如果没有找到配置文件，返回null*/
	public static Properties getPropertiesFromClassPath(String propertiesFileName){
		if(propertiesFileName==null||"".equalsIgnoreCase(propertiesFileName)){
			System.out.println("没有给定properties文件的名字!");
		}
		InputStream input=null;
		Properties properties=new Properties();
		try{
			input=PropertiesTools.class.getClassLoader().getResourceAsStream(propertiesFileName);
			if(input==null){
				return null;
			}
			properties.load(input);
			//System.out.println("位于classpath的Resource目录下的配置文件加载成功!");
			return properties;
		}catch (IOException e) {
			System.out.println("从用classpath加载配置文件失败，可能是文件不存在，或者发生其他IO错误!");
		}finally{
			try {
				input.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return properties;
	}
	/**从普通文件路径获得配置文件
	 * @param filePath 给定的配置文件的全路径
	 * @return 找到的由properties配置文件创建的{@link Properties}对象，如果没有找到配置文件，返回null*/
	public static Properties getPropertiesFromCommonFilePath(String filePath){
		if(filePath==null||"".equalsIgnoreCase(filePath)){
			try {
				throw new Exception("没有给定 properties文件的名字!");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		InputStream input=null;
		Properties properties=new Properties();
		try{
			File propertiesFile=new File(filePath);
			if(!propertiesFile.exists()){
				return null;
			}
			input=new FileInputStream(propertiesFile);
			properties.load(input);
			//System.out.println("位于"+filePath+"的sk配置文件加载成功!");
			return properties;
		}catch (IOException e) {
			try {
				throw new Exception("从用"+filePath+"加载配置文件失败，可能是文件不存在，或者发生其他IO错误!");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}finally{
			try {
				input.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return properties;
	}
	/**
	 * 返回properties中所有的属性和值的map格式
	 * 不支持多值的存在，只会辨认为一个值。
	 * 只支持在类路径下的properties文件
	 * */
	public static Map<String,String> getPropertiesMap(String propertiesName){
		Properties props = getPropertiesFromCommonFilePath(propertiesName);
		Map<String,String> keyInfo = null;
		try{
			keyInfo=new HashMap<String,String>();
			Enumeration<?> en = props.propertyNames();
	        while (en.hasMoreElements()) {
	        	String key = (String) en.nextElement();
	        	String value = props.getProperty (key);
	        	keyInfo.put(key, value);
	        }
		}catch(Exception e){
			e.printStackTrace();
		}
		return keyInfo;
	}

    public static Map<String,String> getPropertiesMap(Properties props){
        Map<String,String> keyInfo = null;
        try{
            keyInfo=new HashMap<String,String>();
            Enumeration<?> en = props.propertyNames();
            while (en.hasMoreElements()) {
                String key = (String) en.nextElement();
                String value = props.getProperty (key);
                keyInfo.put(key, value);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return keyInfo;
    }
	
}
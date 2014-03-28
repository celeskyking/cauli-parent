package org.cauli.junit;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MyFile {
	
	private  List<String> fileList=new ArrayList<String>();
	private static Logger logger = Logger.getLogger(MyFile.class);
	private String filePath;

	File file;
	
	public MyFile(){
		
	}
	public MyFile(String filePath) {

		this.filePath = filePath;

		this.file = new File(filePath);

		if (!file.exists()) {

			createFile(filePath);

		}
	}

	public MyFile(File file) {

		this.filePath = file.getAbsolutePath();

		this.file = file;

	}

	/**根据指定文件地址新建文件，如果有多级目录没有建立，那么建立中间目录*/
	public static boolean createDictory(String path) {

		if (!new File(path).exists()) {

			return new File(path).mkdir();

		}
		return true;
	}
	
	
	public static boolean createFile(String path){
		try {
			return new File(path).createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 跨目录级别建立文件
	 * */
	public static boolean createLevelFile(String path) {

		if (!new File(path).exists()) {

			StringBuilder dictoryPath = new StringBuilder(path);

			dictoryPath.delete(path.lastIndexOf(File.separator), path.length());

			createDictory(dictoryPath.toString());

			try {

				new File(path).createNewFile();

			} catch (IOException e) {

				System.out.println("创建失败！");
			}
		}
		return true;

	}

	public static boolean exist(String path) {

		return new File(path).exists();

	}

	public String getFileContentByString() {

		List<String> fileContentList = getFileContentByList();

		StringBuilder content = new StringBuilder();

		for (String str : fileContentList) {

			content.append(str);

		}
		return content.toString();
	}

	public ArrayList<String> getFileContentByList() {

		if (!new File(filePath).exists()) {

			return null;

		}
		ArrayList<String> fileContent = new ArrayList<String>();

		try {

			BufferedReader reader = new BufferedReader(new FileReader(new File(
					filePath)));

			String temp;

			while ((temp = reader.readLine()) != null) {

				fileContent.add(temp.trim());

			}
			reader.close();

			return fileContent;

		} catch (FileNotFoundException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}
		return null;
	}


	/**删除目录及目录下所有文件和文件夹*/
	public static void deleteDirectory(String directoryPath) {

		File file = new File(directoryPath);

		if (file.isDirectory()) { 

			File temp = null;

			String[] childsFile = file.list(); 

			for (String s : childsFile) {
				
				if (directoryPath.endsWith(File.separator)) {

					temp = new File(directoryPath + s);

				} else {

					temp = new File(directoryPath + File.separator + s);

				}
				// delete file
				if (temp != null && temp.isFile()) { 

					System.out.println(temp.delete());

					System.out.println("delete of the fileName: "
							+ temp.getAbsolutePath());

				} else if (temp != null && temp.isDirectory()) { 

					deleteDirectory(temp.getAbsolutePath());
				}
			}

			file.delete();

		} else if (file.isFile()) { 

			file.delete();

		}
	}

	public boolean rename(String newName) {

		return this.file.renameTo(new File(this.file.getParent() + File.separator
				+ newName));
	}
	/**
	 * @return 得到目录文件下的列表，如果有子目录的话，会遍历子目录
	 * */
	public  List<String> listFile(File f, String suffix, boolean isDepth) {
		
		if (f.isDirectory() && isDepth == true) {

			File[] t = f.listFiles();
			//System.out.println(t.length);
			for (int i = 0; i < t.length; i++) {
				//System.out.println(t[i].getName());
				listFile(t[i], suffix, isDepth);

			}
		} else {

			String filePath = f.getAbsolutePath();
			//System.out.println(filePath);
			if (suffix != null) {

				int begIndex = filePath.lastIndexOf(".");

				String tempsuffix = "";

				if (begIndex != -1) {

					tempsuffix = filePath.substring(begIndex + 1,
							filePath.length());
					//System.out.println(tempsuffix);
				}
				if (tempsuffix.equals(suffix)) {
					//System.out.println(filePath);
					fileList.add(filePath);// 
					//System.out.println("加入成功！");
				}
			}
		}
		//System.out.println(fileList.size());
		return fileList;

	}
	
private static String lineBreak;
	
	public static String lineBreak(){
		String os = System.getProperty("os.name");
		if(os.toLowerCase().contains("win")){
			lineBreak="\r\n";
		}else if(os.toLowerCase().contains("unix")){
			lineBreak="\n";
		}else{
			lineBreak="\r";
		}
		return lineBreak;
	}
	
	
	  public static boolean delAllFile(String path) {
	       boolean flag = false;
	       File file = new File(path);
	       if (!file.exists()) {
	         return flag;
	       }
	       if (!file.isDirectory()) {
	         return flag;
	       }
	       String[] tempList = file.list();
	       File temp = null;
	       for (int i = 0; i < tempList.length; i++) {
	          if (path.endsWith(File.separator)) {
	             temp = new File(path + tempList[i]);
	          } else {
	              temp = new File(path + File.separator + tempList[i]);
	          }
	          if (temp.isFile()) {
	             temp.delete();
	          }
	          if (temp.isDirectory()) {
	             delAllFile(path + File.separator + tempList[i]);//先删除文件夹里面的文件
	             delFolder(path + File.separator + tempList[i]);//再删除空文件夹
	             flag = true;
	          }
	       }
	       return flag;
	     }
	
	  public static void delFolder(String folderPath) {
		     try {
		        delAllFile(folderPath); //删除完里面所有内容
		        String filePath = folderPath;
		        filePath = filePath.toString();
		        File myFilePath = new File(filePath);
		        myFilePath.delete(); //删除空文件夹
		     } catch (Exception e) {
		       e.printStackTrace(); 
		     }
	  }
	
	  public static void delAllFile(String path,String suffix) {
	       File file = new File(path);
	       if(!file.exists()){
	    	  logger.warn("不存在要删除的页面，请检查一下目录是否正确");
	       }else{
	    	   File[] files = file.listFiles();
		       for(File f: files){
		    	   if(f.getName().endsWith(suffix)){
		    		   f.delete();
		    	   }
		       }
	       }
	          
	     }
	  
	  public static void delAllFileByStart(String path,String start){
		  File file = new File(path);
	       if(!file.exists()){
	    	  logger.warn("不存在要删除的目录，请检查一下目录是否正确");
	       }else{
	    	   File[] files = file.listFiles();
		       for(File f: files){
		    	   if(f.getName().startsWith(start)){
		    		   f.delete();
		    	   }
		       }
		       logger.info("清空了以前的日志文件");
	       }
	  }
	  
}

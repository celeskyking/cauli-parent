package org.cauli.junit.build;

import org.cauli.junit.FrameworkMethodWithParameters;
import org.cauli.junit.ParameterGenerator;
import org.cauli.junit.anno.Param;
import org.junit.Test;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.TestClass;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by celeskyking on 2014/6/1
 */
public class DefaultFrameworksBuilder  implements FrameworksBuilder{
    @Override
    public List<FrameworkMethod> build(TestClass testClass) {
        List<FrameworkMethod> children = new ArrayList<FrameworkMethod>();
        for (FrameworkMethod method : testClass.getAnnotatedMethods(Test.class)) {
            if (method.getMethod().getParameterTypes().length == 0) {
                // standard JUnit generator method
                children.add(method);
                continue;
            } else if(method.getMethod().isAnnotationPresent(Param.class)){
                Param param = method.getAnnotation(Param.class);
                String path = param.value();
                String type = param.type();
                File file;
                if("default".equals(path)){
                    try {
                        file=getSimilarFile("data/"+method.getMethod().getDeclaringClass().getSimpleName(),method.getName());
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new RuntimeException("文件没有找到,查找的目录不存在。在进行参数化的时候出现了错误...",e);
                    }
                }else{
                    file= new File(path);
                }
                ParameterGenerator generator = new ParameterGenerator(file);
                generator.setReadType(type);
                List<FrameworkMethodWithParameters> methods;
                try {
                    methods = generator.generator(method);
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException("初始化方法的时候出现了错误.."+method.getName());
                }
                children.addAll(methods);
            }
        }
        return children;
    }


    private File getSimilarFile(String dir,String partFileName) throws FileNotFoundException {
        File dirFile = new File(dir);
        if(dirFile==null){
            throw new FileNotFoundException("文件没有找到,查找的目录不存在");
        }
        if(dirFile.isDirectory()){
            File[] files = dirFile.listFiles();
            for(File file:files){
                if(file.getName().contains(partFileName)){
                    return file;
                }
            }
        }else if(!dirFile.isDirectory()){
            return dirFile;
        }else{
            throw new FileNotFoundException("文件没有找到,查找的目录不存在");
        }
        return null;
    }
}

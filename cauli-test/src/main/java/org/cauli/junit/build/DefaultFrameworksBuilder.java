package org.cauli.junit.build;

import org.cauli.exception.FrameworkBuildException;
import org.cauli.junit.AnnotationParameterProvider;
import org.cauli.junit.FrameworkMethodWithParameters;
import org.junit.Test;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.TestClass;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by celeskyking on 2014/6/1
 */
public class DefaultFrameworksBuilder  implements FrameworksBuilder{
    @Override
    public List<FrameworkMethodWithParameters> build(TestClass testClass) throws FrameworkBuildException {
        List<FrameworkMethodWithParameters> children = new ArrayList<FrameworkMethodWithParameters>();
        for (FrameworkMethod method : testClass.getAnnotatedMethods(Test.class)) {
            AnnotationParameterProvider provider = new AnnotationParameterProvider();
                List<FrameworkMethodWithParameters> methods;
                try {
                    methods = provider.generator(method);
                } catch (Exception e) {
                    throw new FrameworkBuildException("初始化方法的时候出现了错误.."+method.getName(),e);
                }
            children.addAll(methods);
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

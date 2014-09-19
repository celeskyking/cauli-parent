package org.cauli.junit.build;

import com.google.common.collect.Lists;
import org.cauli.exception.FrameworkBuildException;
import org.cauli.junit.AnnotationParameterProvider;
import org.cauli.junit.FrameworkMethodWithParameters;
import org.junit.Test;
import org.junit.runners.model.TestClass;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by celeskyking on 2014/6/1
 */
public class DefaultFrameworksBuilder  implements FrameworksBuilder{

    public List<FrameworkMethodWithParameters> getAnnotationFrameworks(Class clazz){
        if (clazz != null && clazz.getConstructors().length > 1) {
            throw new IllegalArgumentException(
                    "Test class can only have one constructor");
        }
        List<FrameworkMethodWithParameters> methodWithParameterses = Lists.newArrayList();
        for(Method method:clazz.getMethods()){
            if(method.isAnnotationPresent(Test.class)){
                methodWithParameterses.add(new FrameworkMethodWithParameters(method));
            }
        }
        return methodWithParameterses;
    }
    @Override
    public List<FrameworkMethodWithParameters> build(TestClass testClass) {
        List<FrameworkMethodWithParameters> children = new ArrayList<FrameworkMethodWithParameters>();
        for (FrameworkMethodWithParameters method : getAnnotationFrameworks(testClass.getJavaClass())) {
            AnnotationParameterProvider provider = new AnnotationParameterProvider();
                List<FrameworkMethodWithParameters> methods;
                try {
                    methods = provider.generator(method);
                } catch (Exception e) {
                    throw new FrameworkBuildException("初始化方法的时候出现了错误.."+method.getMethod().getName(),e);
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

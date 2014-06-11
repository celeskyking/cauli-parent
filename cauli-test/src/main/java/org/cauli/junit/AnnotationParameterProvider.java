package org.cauli.junit;

import com.google.common.collect.Lists;
import org.cauli.exception.ConverterError;
import org.cauli.exception.FileGeneratorException;
import org.cauli.instrument.MethodUtils;
import org.cauli.junit.anno.Dependency;
import org.cauli.junit.anno.Param;
import org.cauli.junit.anno.Tag;
import org.cauli.junit.build.NamedConverter;
import org.cauli.junit.info.DefaultInfoProvider;
import org.junit.runners.model.FrameworkMethod;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by tianqing.wang on 2014/6/6
 */
public class AnnotationParameterProvider implements ParameterProvider{

    private File file;
    private FileGenerator fileGenerator;
    private String readType;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public FileGenerator getFileGenerator() {
        return fileGenerator;
    }

    public void setFileGenerator(FileGenerator fileGenerator) {
        this.fileGenerator = fileGenerator;
    }

    public String getReadType() {
        return readType;
    }

    public void setReadType(String readType) {
        this.readType = readType;
    }

    @Override
    public List<FrameworkMethodWithParameters> generator(FrameworkMethodWithParameters method) throws IOException, FileGeneratorException {
        List<FrameworkMethodWithParameters> result = Lists.newArrayList();
        FrameworkMethodWithParameters frameworkMethodWithParameters = new FrameworkMethodWithParameters(method.getMethod());
        setFile(getParamFile(method));
        if (null==file||!file.exists()) {
            frameworkMethodWithParameters.setDependencyMethodName(method.getAnnotation(Dependency.class) == null ? null : method.getAnnotation(Dependency.class).value());
            Tag tag = frameworkMethodWithParameters.getAnnotation(Tag.class);
            if(tag!=null){
                frameworkMethodWithParameters.setName(tag.name());
                frameworkMethodWithParameters.setLevel(tag.level());
            }else{
                frameworkMethodWithParameters.setName(method.getMethod().getName());
            }
            result.add(frameworkMethodWithParameters);
            return result;
        } else {
            this.fileGenerator = FileGeneratorFactory.loadGenerator().createFileGenerator(file,readType);
        }
        List<RowParameter> rowParameters = fileGenerator.generator();
        List<String> headers = fileGenerator.getHeaders();
        for (RowParameter parameter : rowParameters) {
            try {
                FrameworkMethodWithParameters parameters = parseMethod(method, parameter,headers);
                result.add(parameters);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("配置文件有错误...导致bean加载失败,file:" + this.file.getName(), e);

            }

        }
        return result;
    }


    private FrameworkMethodWithParameters parseMethod(FrameworkMethodWithParameters frameworkMethodWithParameters,RowParameter parameter,List<String> headers) throws ConverterError {
        Class[] classes = frameworkMethodWithParameters.getMethod().getParameterTypes();
        Object[] objects = new Object[classes.length];
        for(int i=0;i<classes.length;i++){
            Class<? extends Annotation> annotationType = MethodUtils.getParameterOnlyAnnotationType(frameworkMethodWithParameters.getMethod(), i);
            Annotation annotation = MethodUtils.getParameterOnlyAnnotation(frameworkMethodWithParameters.getMethod(),i);
            try {
                GeneratorConverter converter = GeneratorManager.getGeneratorConverter(annotationType);
                objects[i] =converter.convert(annotation,classes[i],parameter,headers);
            } catch (Exception e) {
                throw new ConverterError("解析方法"+frameworkMethodWithParameters.getName()+"参数bean的时候出现了错误..",e);
            }
        }
        DefaultInfoProvider infoProvider = new DefaultInfoProvider();
        FrameworkMethodWithParameters method = new FrameworkMethodWithParameters(frameworkMethodWithParameters.getMethod());
        method.setInfo(infoProvider.testInfo(method.getMethod(),objects));
        method.setParameters(objects);
        method.setDependencyMethodName(method.getAnnotation(Dependency.class) == null ? null : method.getAnnotation(Dependency.class).value());
        Tag tag = method.getAnnotation(Tag.class);
        if(tag!=null){
            method.setName(tag.name());
            method.setLevel(tag.level());
        }else{
            method.setName(method.getMethod().getName());
        }
        return method;
    }



    private File getParamFile(FrameworkMethod frameworkMethod) throws FileNotFoundException {
        Param param = frameworkMethod.getAnnotation(Param.class);
        if(param!=null){
            if("default".equals(param.value())){
                setReadType(param.type());
                return getSimilarFile("data/"+frameworkMethod.getMethod().getDeclaringClass().getSimpleName(),frameworkMethod.getName());
            }else{
                setReadType(param.type());
                return new File(param.value());
            }
        }
        return null;
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
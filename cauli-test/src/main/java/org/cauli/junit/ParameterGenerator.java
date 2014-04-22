package org.cauli.junit;

import com.google.common.collect.Lists;
import jodd.typeconverter.TypeConverterManager;
import org.cauli.db.DBCore;
import org.cauli.db.DbManager;
import org.cauli.db.annotation.Field;
import org.cauli.db.annotation.SQL;
import org.cauli.instrument.BeanUtils;
import org.cauli.instrument.ClassUtils;
import org.cauli.instrument.MethodUtils;
import org.cauli.junit.anno.Bean;
import org.cauli.junit.info.DefaultInfoProvider;
import org.junit.runners.model.FrameworkMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * Created by tianqing.wang on 14-2-28
 */
public class ParameterGenerator implements ParameterProvider {
    private File file;
    private FileGenerator fileGenerator;
    private String readType;
    private Logger logger = LoggerFactory.getLogger(ParameterGenerator.class);

    public ParameterGenerator(File file) {
        this.file = file;
        this.readType = "row";
    }

    public String getReadType() {
        return readType;
    }

    public void setReadType(String readType) {
        this.readType = readType;
    }

    public List<FrameworkMethodWithParameters> generator(FrameworkMethod method) throws IOException {
        List<FrameworkMethodWithParameters> result = Lists.newArrayList();
        if (!file.exists()) {
            throw new RuntimeException(getClass().getName() + "找不到文件...");
        } else {
            this.fileGenerator = FileGeneratorFactory.createFileGenerator(file, method.getMethod(), getReadType());
        }
        List<RowParameter> rowParameters = fileGenerator.generator();
        for (RowParameter parameter : rowParameters) {
            try {
                FrameworkMethodWithParameters parameters = parseBeanMethod(method.getMethod(), parameter);
                result.add(parameters);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("配置文件有错误...导致bean加载失败,file:" + this.file.getName(), e);

            }

        }
        return result;
    }

    public FileGenerator getFileGenerator() {
        return fileGenerator;
    }

    public void setFileGenerator(FileGenerator fileGenerator) {
        this.fileGenerator = fileGenerator;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    private FrameworkMethodWithParameters parseBeanMethod(Method method, RowParameter rowParameter) throws Exception {
        Class[] classes = method.getParameterTypes();
        Object[] objects = new Object[classes.length];
        int j = 0;
        for (Class<?> clazz : classes) {
            if (MethodUtils.isParameterAnnotationPresent(method, Bean.class, j)) {
                Bean bean = MethodUtils.getParameterAnnotation(method,Bean.class,j);
                String value = bean.value();
                Object object = clazz.newInstance();
                List<String> headers = this.fileGenerator.getHeaders();
                for (int i = 0; i < headers.size(); i++) {
                    if (headers.get(i).contains(value)) {
                        String beanValue = org.apache.commons.lang3.StringUtils.substringAfter(headers.get(i), ".");
                        BeanUtils.setProperty(object, beanValue, rowParameter.getParams().get(i));
                    }
                }
                objects[j] = object;
            }  else {
                String string = rowParameter.getParams().get(j);
                objects[j]= TypeConverterManager.convertType(string,clazz);
            }
            j++;
        }
        DefaultInfoProvider infoProvider = new DefaultInfoProvider();
        FrameworkMethodWithParameters frameworkMethodWithParameters = new FrameworkMethodWithParameters(method, objects, infoProvider.testInfo(method, objects));
        return frameworkMethodWithParameters;
    }
}

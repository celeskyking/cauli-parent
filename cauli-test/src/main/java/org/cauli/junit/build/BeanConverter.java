package org.cauli.junit.build;

import org.cauli.exception.BeanClassNotMatchException;
import org.cauli.instrument.BeanUtils;
import org.cauli.junit.GeneratorConverter;
import org.cauli.junit.RowParameter;
import org.cauli.junit.anno.Bean;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by tianqing.wang on 2014/6/6
 */
public class BeanConverter implements GeneratorConverter<Bean,Object>{
    @Override
    public Object convert(Bean bean, Class<Object> clazz, RowParameter parameter,List<String> headers) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        String value = bean.value();
        Object object;
        try {
            Constructor constructor = clazz.getConstructor();
            object = constructor.newInstance();
        } catch (NoSuchMethodException e) {
            throw new BeanClassNotMatchException("Bean.class注解只能应用于Bean方法里面,class构造方法有错误");
        }

        for (int i = 0; i < headers.size(); i++) {
            if (headers.get(i).contains(value)) {
                String beanValue = org.apache.commons.lang3.StringUtils.substringAfter(headers.get(i), ".");
                BeanUtils.setProperty(object, beanValue, VauleUtil.valueTransfer(parameter.getParams().get(i)));
            }
        }
        return object;

    }

    @Override
    public Class<Bean> genAnnotationType() {
        return Bean.class;
    }
}

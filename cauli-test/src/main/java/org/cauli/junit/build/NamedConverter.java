package org.cauli.junit.build;

import jodd.typeconverter.TypeConverterManager;
import org.cauli.exception.BeanClassNotMatchException;
import org.cauli.instrument.BeanUtils;
import org.cauli.junit.GeneratorConverter;
import org.cauli.junit.RowParameter;
import org.cauli.junit.anno.Bean;
import org.cauli.junit.anno.Named;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by tianqing.wang on 2014/6/9
 */
public class NamedConverter implements GeneratorConverter<Named,Object> {
    @Override
    public Object convert(Named t, Class<Object> v, RowParameter parameter, List<String> headers) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        String value = t.value();
        Object object;
        try {
            Constructor constructor = v.getConstructor();
            object = constructor.newInstance();
        } catch (NoSuchMethodException e) {
            throw new BeanClassNotMatchException("Bean.class注解只能应用于Bean方法里面,class构造方法有错误");
        }

        for (int i = 0; i < headers.size(); i++) {
            if (headers.get(i).equals(value)) {
                object= TypeConverterManager.convertType(parameter.getParams().get(i),Object.class);
            }
        }
        return object;
    }

    @Override
    public Class<Named> genAnnotationType() {
        return Named.class;
    }


}

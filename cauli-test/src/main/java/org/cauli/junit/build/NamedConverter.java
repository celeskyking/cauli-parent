package org.cauli.junit.build;

import jodd.typeconverter.TypeConverterManager;
import org.cauli.exception.BeanClassNotMatchException;
import org.cauli.junit.GeneratorConverter;
import org.cauli.junit.PairParameter;
import org.cauli.junit.anno.Named;
import org.cauli.pairwise.core.ParameterValuePair;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by tianqing.wang on 2014/6/9
 */
public class NamedConverter implements GeneratorConverter<Named,Object> {
    @Override
    public Object convert(Named t, Class<Object> v, PairParameter pairParameter) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        String value = t.value();
        Object object;
        try {
            Constructor constructor = v.getConstructor();
            object = constructor.newInstance();
        } catch (NoSuchMethodException e) {
            throw new BeanClassNotMatchException("Bean.class注解只能应用于Bean方法里面,class构造方法有错误");
        }

        for (ParameterValuePair pair:pairParameter.getPairs()) {
            if (pair.getParameterName().startsWith(value)) {
                object= TypeConverterManager.convertType(pair.getParameterValue(),Object.class);
            }
        }
        return object;
    }

    @Override
    public Class<Named> genAnnotationType() {
        return Named.class;
    }


}

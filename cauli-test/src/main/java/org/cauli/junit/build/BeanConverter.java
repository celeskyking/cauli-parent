package org.cauli.junit.build;

import org.cauli.exception.BeanClassNotMatchException;
import org.cauli.instrument.BeanUtils;
import org.cauli.junit.GeneratorConverter;
import org.cauli.junit.PairParameter;
import org.cauli.junit.RowParameter;
import org.cauli.junit.anno.Bean;
import org.cauli.pairwise.core.ParameterValuePair;
import org.cauli.template.ValueTransfer;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * Created by tianqing.wang on 2014/6/6
 */
public class BeanConverter implements GeneratorConverter<Bean,Object>{
    @Override
    public Object convert(String parameterName,Class<Object> clazz, PairParameter pairParameter,Map<String,Class<?>> paramTypes) throws Exception {
        Object object;
        try {
            Constructor constructor = clazz.getConstructor();
            object = constructor.newInstance();
        } catch (NoSuchMethodException e) {
            throw new BeanClassNotMatchException("Bean.class注解只能应用于Bean方法里面,class构造方法有错误");
        }

        for (ParameterValuePair pair:pairParameter.getPairs()) {
            if (pair.getParameterName().startsWith(parameterName)) {
                String beanValue = org.apache.commons.lang3.StringUtils.substringAfter(pair.getParameterName(), ".");
                BeanUtils.setProperty(object, beanValue, VauleUtil.valueTransfer(ValueTransfer.getValue(pair.getParameterValue(), pairParameter,paramTypes)));
            }
        }
        return object;

    }



    @Override
    public Class<Bean> genAnnotationType() {
        return Bean.class;
    }
}

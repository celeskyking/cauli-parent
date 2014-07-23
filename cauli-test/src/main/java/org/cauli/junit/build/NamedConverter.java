package org.cauli.junit.build;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import jodd.typeconverter.TypeConverterManager;
import org.apache.commons.lang3.StringUtils;
import org.cauli.exception.NamedConverterException;
import org.cauli.instrument.ClassUtils;
import org.cauli.junit.GeneratorConverter;
import org.cauli.junit.PairParameter;
import org.cauli.junit.anno.Named;
import org.cauli.pairwise.core.ParameterValuePair;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * Created by tianqing.wang on 2014/6/9
 *
 * 只支持各种基础类型和Map<String,String>和List<String>
 */
public class NamedConverter implements GeneratorConverter<Named,Object> {
    @Override
    public Object convert(Named t, Class<Object> v, PairParameter pairParameter) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        String value = t.value();
        Object object = null;
        try {
            if(isListType(v)){
                object= Lists.newArrayList();
                for (ParameterValuePair pair:pairParameter.getPairs()) {
                    if (pair.getParameterName().startsWith(value)) {
                        ((List)object).add(pair.getParameterValue());
                    }
                }
            }else if(isMapType(v)){
                object= Maps.newHashMap();
                for (ParameterValuePair pair:pairParameter.getPairs()) {
                    if (pair.getParameterName().startsWith(value)) {
                        ((Map)object).put(StringUtils.substringAfter(pair.getParameterName(), "."), pair.getParameterValue());
                    }
                }
            }else if(v.isInterface()){
                throw new NamedConverterException("不支持的注入类型"+v.getName());
            }else{
                for (ParameterValuePair pair:pairParameter.getPairs()) {
                    if (pair.getParameterName().startsWith(value)) {
                        object= TypeConverterManager.convertType(pair.getParameterValue(),v);
                    }
                }
            }
        } catch (Exception e) {
            throw new NamedConverterException("Named.class注解注入参数错误,可能不支持该类型"+v.getName(),e);
        }
        return object;
    }

    @Override
    public Class<Named> genAnnotationType() {
        return Named.class;
    }


    public boolean isMapType(Class<?> clazz){
        return ClassUtils.isAssignableFromSubClass(Map.class, clazz);
    }

    public boolean isListType(Class<?> clazz){
        return ClassUtils.isAssignableFromSubClass(List.class,clazz);
    }


}

package org.cauli.junit;

import com.google.common.collect.Maps;
import org.cauli.junit.anno.Bean;
import org.cauli.junit.anno.Named;
import org.cauli.junit.build.BeanConverter;
import org.cauli.junit.build.NamedConverter;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tianqing.wang on 2014/6/6
 */
public class GeneratorManager {

    private final static Map<Class<? extends Annotation>,GeneratorConverter> converterMap = Maps.newHashMap();
    static{
        converterMap.put(Named.class,new NamedConverter());
        converterMap.put(Bean.class,new BeanConverter());
    }

    public static  void register(GeneratorConverter converter){
        converterMap.put(converter.genAnnotationType(),converter);
    }


    public static GeneratorConverter getGeneratorConverter(Class<? extends Annotation> annotation){
        return converterMap.get(annotation);
    }
}

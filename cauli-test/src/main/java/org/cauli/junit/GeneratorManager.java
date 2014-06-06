package org.cauli.junit;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tianqing.wang on 2014/6/6
 */
public class GeneratorManager {

    private final static Map<Class<Annotation>,GeneratorConverter> converterMap = new HashMap<Class<Annotation>, GeneratorConverter>();

    public static  void register(GeneratorConverter converter){
        converterMap.put(converter.genAnnotationType(),converter);
    }


    public static GeneratorConverter getGeneratorConverter(Annotation annotation){
        return converterMap.get(annotation);
    }
}

package org.cauli.junit;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * Created by celeskyking on 2014/4/23
 */
public interface GeneratorConverter<K extends Annotation,V> {

    public V convert(String parameterName, Class<V> v,PairParameter pairParameter,Map<String,Class<?>> paramTypes) throws Exception;

    public Class<K> genAnnotationType();
}

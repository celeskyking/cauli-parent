package org.cauli.junit;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by celeskyking on 2014/4/23
 */
public interface GeneratorConverter<K extends Annotation,V> {

    public V convert(K t, Class<V> v,PairParameter pairParameter) throws IllegalAccessException, InstantiationException, InvocationTargetException;

    public Class<K> genAnnotationType();
}

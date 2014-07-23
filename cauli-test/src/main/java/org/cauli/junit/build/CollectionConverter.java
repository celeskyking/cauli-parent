package org.cauli.junit.build;

import org.cauli.junit.GeneratorConverter;
import org.cauli.junit.PairParameter;
import org.cauli.junit.anno.Collection;

import java.lang.reflect.InvocationTargetException;


/**
 * Created by tianqing.wang on 2014/6/17
 */
public class CollectionConverter implements GeneratorConverter<Collection,java.util.Collection>{


    @Override
    public java.util.Collection convert(Collection t, Class<java.util.Collection> v, PairParameter pairParameter) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        return null;
    }

    @Override
    public Class<Collection> genAnnotationType() {
        return Collection.class;
    }
}

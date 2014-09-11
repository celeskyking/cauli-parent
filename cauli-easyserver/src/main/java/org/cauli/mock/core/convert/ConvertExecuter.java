package org.cauli.mock.core.convert;

import org.cauli.mock.entity.ParameterValuePairs;

/**
 * @auther sky
 */
public interface ConvertExecuter<K,V> {

    public V execute(K clazz, ParameterValuePairs parameterValuePairs);
}

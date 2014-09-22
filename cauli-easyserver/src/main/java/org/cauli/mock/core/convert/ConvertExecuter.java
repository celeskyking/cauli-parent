package org.cauli.mock.core.convert;

import org.cauli.mock.entity.ParametersModel;

/**
 * @auther sky
 */
public interface ConvertExecuter<K,V> {

    public V execute(K clazz, ParametersModel parameterValuePairs);
}

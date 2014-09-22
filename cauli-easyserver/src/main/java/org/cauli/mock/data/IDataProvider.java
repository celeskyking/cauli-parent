package org.cauli.mock.data;

import org.cauli.mock.entity.KeyValueStores;

/**
 * @auther sky
 */
public interface IDataProvider {


    public KeyValueStores loadDatas(String section);
}

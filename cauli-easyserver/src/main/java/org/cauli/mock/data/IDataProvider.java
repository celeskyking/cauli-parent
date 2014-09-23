package org.cauli.mock.data;

import org.cauli.mock.context.Context;
import org.cauli.mock.entity.KeyValueStores;

import java.util.Comparator;

/**
 * @auther sky
 */
public interface IDataProvider {


    public KeyValueStores loadDatas(Context context,String file,String section,Comparator comparator);
}

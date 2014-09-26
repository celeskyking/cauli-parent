package org.cauli.mock.data;

import org.cauli.mock.ValueHandler;
import org.cauli.mock.context.Context;
import org.cauli.mock.entity.KeyValueStores;
import org.cauli.mock.util.PropUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Comparator;

/**
 * Created by tianqing.wang on 2014/9/22
 */
public class DefaultDataProvider implements IDataProvider{


    private Logger logger = LoggerFactory.getLogger(DefaultDataProvider.class);


    @Override
    public KeyValueStores loadDatas(Context context,String source,String section,Comparator comparator,ValueHandler valueHandler) {
        try {
            return PropUtil.loadFileByClasspath(context,source, section, comparator,valueHandler);
        } catch (Exception e) {
            logger.error("加载{}失败",source,e);
            return null;
        }
    }


}

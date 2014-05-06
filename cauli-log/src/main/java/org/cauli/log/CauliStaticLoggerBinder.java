package org.cauli.log;

import org.slf4j.ILoggerFactory;
import org.slf4j.spi.LoggerFactoryBinder;

/**
 * Created by tianqing.wang on 2014/5/4
 */
public class CauliStaticLoggerBinder implements LoggerFactoryBinder{
    @Override
    public ILoggerFactory getLoggerFactory() {
        return null;
    }

    @Override
    public String getLoggerFactoryClassStr() {
        return null;
    }
}

package org.cauli.mock.strategy;

import org.cauli.mock.context.Context;

/**
 * Created by tianqing.wang on 2014/9/19
 */
public interface IStrategy<T> {

    public String process(T request,Context context);

}

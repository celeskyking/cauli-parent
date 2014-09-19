package org.cauli.mock.context;

import org.cauli.server.HttpRequest;

/**
 * Created by tianqing.wang on 2014/9/19
 */
public interface IStrategy {

    public String process(HttpRequest request);

}

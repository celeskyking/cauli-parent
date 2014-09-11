package org.cauli.mock.server;

/**
 * Created by tianqing.wang on 2014/9/9
 */
public interface IServerCreator<T extends MockServer> {

    public T create(String serverName,int port);
}

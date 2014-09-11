package org.cauli.mock;

/**
 * Created by tianqing.wang on 2014/9/1
 */
public interface ServerDispatcher {

    void startServer(String serverName);

    void stopServer(String serverName);

    void createServer(ServerStyle style,int port);

}

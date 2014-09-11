package org.cauli.mock.server;

import org.cauli.mock.ServerProtocol;
import org.cauli.mock.ServerStatus;

/**
 * Created by tianqing.wang on 2014/9/1
 */
public interface IServer {

    public void start() throws Exception;

    public void stop() throws Exception;

    public ServerStatus getServerStatus();

    public ServerProtocol getProtocol();
}

package org.cauli.mock.server;

import org.cauli.mock.ServerBuilder;
import org.cauli.mock.entity.ServerInfo;

/**
 * Created by tianqing.wang on 2014/9/9
 *
 * 暂时不支持创建SSL协议的server
 */
public class HttpServerCreator implements IServerCreator<AbstractHttpServer>{


    @Override
    public AbstractHttpServer create(final String serverName, final int port) {
        AbstractHttpServer server=new AbstractHttpServer() {
            @Override
            public void config(ServerInfo serverInfo) {
                serverInfo.setServerName(serverName);
                serverInfo.setPort(port);
            }
        };
        ServerBuilder.getInstance().addUsedPort(port);
        return server;

    }
}

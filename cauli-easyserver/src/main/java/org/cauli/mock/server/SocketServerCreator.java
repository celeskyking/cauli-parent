package org.cauli.mock.server;

import org.cauli.mock.ServerBuilder;
import org.cauli.mock.action.AbstractSocketAction;
import org.cauli.mock.constant.Constant;
import org.cauli.mock.entity.ServerInfo;

/**
 * Created by tianqing.wang on 2014/9/9
 */
public class SocketServerCreator implements IServerCreator<AbstractSocketServer> {
    @Override
    public AbstractSocketServer create(final String serverName, final int port) {
        AbstractSocketServer socketServer = new AbstractSocketServer() {
            @Override
            public AbstractSocketAction route(String request) {
                if(getAction(Constant.DEFAULT_ACTION_NAME)==null){
                    createAction(Constant.DEFAULT_ACTION_NAME,"","SUCCESS","");
                }

                return getAction(Constant.DEFAULT_ACTION_NAME);
            }

            @Override
            public void config(ServerInfo serverInfo) {
                serverInfo.setServerName(serverName);
                serverInfo.setPort(port);
            }



        };
        ServerBuilder.getInstance().addUsedPort(port);
        return socketServer;
    }
}

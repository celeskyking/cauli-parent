package org.cauli.easyserver.test;

import org.cauli.mock.ConfigType;
import org.cauli.mock.ServerInitStatus;
import org.cauli.mock.ServerManager;
import org.cauli.mock.action.AbstractSocketAction;
import org.cauli.mock.annotation.Action;
import org.cauli.mock.entity.ActionInfo;
import org.cauli.mock.entity.ServerInfo;
import org.cauli.mock.server.AbstractSocketServer;

/**
 * Created by tianqing.wang on 2014/10/31
 */
public class MySocketServer extends AbstractSocketServer{
    @Override
    public AbstractSocketAction route(String request) {
        return test;
    }



    @Override
    public void config(ServerInfo serverInfo) {
        serverInfo.setPort(7777);
        serverInfo.setInitStatus(ServerInitStatus.START);
    }


    public AbstractSocketAction test = new AbstractSocketAction("test") {
        @Override
        public void config(ActionInfo actionInfo) {
            actionInfo.setUseTemplate(false);
        }

        @Action(ConfigType.TEMPLATE)
        public void configResponse(){
            setTemplateValue("HELLO,NIO");
        }
    };

    public static void main(String[] args) throws Exception {
        new ServerManager(false).init();
    }
}

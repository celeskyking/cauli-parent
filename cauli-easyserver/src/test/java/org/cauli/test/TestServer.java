package org.cauli.test;

import org.cauli.mock.ConfigType;
import org.cauli.mock.ServerInitStatus;
import org.cauli.mock.action.AbstractHttpAction;
import org.cauli.mock.annotation.Action;
import org.cauli.mock.entity.ActionInfo;
import org.cauli.mock.entity.ServerInfo;
import org.cauli.mock.server.AbstractHttpServer;

/**
 * Created by tianqing.wang on 2014/9/23
 */
public class TestServer extends AbstractHttpServer{
    @Override
    public void config(ServerInfo serverInfo) {
        serverInfo.setPort(9998);
        serverInfo.setInitStatus(ServerInitStatus.START);
    }


    public AbstractHttpAction action = new AbstractHttpAction("action") {
        @Override
        public void config(ActionInfo actionInfo) {
            actionInfo.setUseTemplate(false);
            actionInfo.setRequestUri("/yes");
        }

        @Action(ConfigType.TEMPLATE)
        public void configTemplateValue(){
            setTemplateValue("yes");
        }

    };
}

package org.cauli.test;

import org.cauli.mock.ConfigType;
import org.cauli.mock.ServerInitStatus;
import org.cauli.mock.action.AbstractHttpAction;
import org.cauli.mock.annotation.Action;
import org.cauli.mock.entity.ActionInfo;
import org.cauli.mock.entity.ServerInfo;
import org.cauli.mock.server.AbstractHttpServer;
import org.cauli.server.annotation.Path;

/**
 * Created by tianqing.wang on 2014/9/19
 */
public class HelloServer extends AbstractHttpServer{
    @Override
    public void config(ServerInfo serverInfo) {
        serverInfo.setPort(9999);
        serverInfo.setInitStatus(ServerInitStatus.START);
    }

    @Path("/hello")
    public AbstractHttpAction hello = new AbstractHttpAction() {
        @Override
        public void config(ActionInfo httpActionInfo) {
            httpActionInfo.setUseTemplate(false);
        }

        @Action(ConfigType.TEMPLATE)
        public void configTemplateValue(){
            setTemplateValue("hello,world");
        }

    };
}

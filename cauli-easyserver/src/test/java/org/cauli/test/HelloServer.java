package org.cauli.test;

import org.cauli.mock.ServerInitStatus;
import org.cauli.mock.action.AbstractHttpAction;
import org.cauli.mock.annotation.CallBack;
import org.cauli.mock.entity.ActionInfo;
import org.cauli.mock.entity.KeyValueStores;
import org.cauli.mock.entity.ParametersModel;
import org.cauli.mock.entity.ServerInfo;
import org.cauli.mock.exception.ActionExecuteException;
import org.cauli.mock.server.AbstractHttpServer;
import org.cauli.server.HttpMethod;

/**
 * Created by tianqing.wang on 2014/9/19
 */
public class HelloServer extends AbstractHttpServer{
    @Override
    public void config(ServerInfo serverInfo) {
        serverInfo.setPort(9999);
        serverInfo.setInitStatus(ServerInitStatus.START);
    }

    public AbstractHttpAction hello = new AbstractHttpAction("hello") {
        @Override
        public void config(ActionInfo actionInfo) {
            actionInfo.setUseMessage(true);
            actionInfo.getCallbackInfo().http.setMethod(HttpMethod.POST);
            actionInfo.getCallbackInfo().http.setUrl("http://localhost:9998/yes");
        }

        @CallBack
        public String helloCallback(){
            KeyValueStores stores = loadData(getReturnStatus());
           return sender().body(callbackContent()).send().bodyText();

        }

        @Override
        public void onMessage(ParametersModel pairs) {
            delay(3);
            try {
                String response = callback();
                System.out.println(response);
            } catch (ActionExecuteException e) {
                e.printStackTrace();
            }
        }
    };

}

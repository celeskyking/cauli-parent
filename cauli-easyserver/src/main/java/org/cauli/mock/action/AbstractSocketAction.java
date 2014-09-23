package org.cauli.mock.action;

import org.cauli.mock.sender.SocketSender;

import java.nio.charset.Charset;

/**
 * Created by tianqing.wang on 2014/8/29
 */
public abstract class AbstractSocketAction extends AbstractAction<String,String>{

    public AbstractSocketAction(String actionName){
        super(actionName);
    }

    public SocketSender sender(){
        String host = getActionInfo().getCallbackInfo().socket.getHost();
        int port = getActionInfo().getCallbackInfo().socket.getPort();
        Charset charset =getActionInfo().getCallbackInfo().socket.getCharset();
        boolean isAync = getActionInfo().getCallbackInfo().socket.isAync();
        return new SocketSender().aync(isAync).host(host).port(port).encoding(charset);
    }
}

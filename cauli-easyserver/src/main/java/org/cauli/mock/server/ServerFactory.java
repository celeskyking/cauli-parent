package org.cauli.mock.server;

import org.cauli.mock.ServerStyle;

/**
 * Created by tianqing.wang on 2014/9/9
 */
public class ServerFactory {

    public static MockServer create(ServerStyle style,String serverName,int port){
        if(style==ServerStyle.HTTP){
            return new HttpServerCreator().create(serverName,port);
        }else if(style==ServerStyle.SOCKET){
            return new HttpServerCreator().create(serverName,port);
        }else{
            throw new RuntimeException("不支持的协议的Server"+style.toString());
        }
    }
}

package org.cauli.server.factory;


import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import org.cauli.server.WebServer;
import org.cauli.server.WebServers;
import java.util.concurrent.Executor;


/**
 * Created by tianqing.wang on 2014/6/24
 */
public class ServerFactory {

    public static SocketIOServer newSocketIOServer(String host,int port){
        Configuration configuration = new Configuration();
        configuration.setHostname(host);
        configuration.setPort(port);
        SocketIOServer socketIOServer = new SocketIOServer(configuration);
        return socketIOServer;
    }


    public static WebServer newWebServer(int port){
        WebServer server = WebServers.createWebServer(port);
        return server;
    }

    public static WebServer newWebServer(Executor executor,int port){
        WebServer server = WebServers.createWebServer(executor,port);
        return server;
    }




}

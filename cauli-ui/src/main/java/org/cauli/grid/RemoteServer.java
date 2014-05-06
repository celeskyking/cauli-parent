package org.cauli.grid;

import org.openqa.selenium.server.SeleniumServer;

/**
 * Created by tianqing.wang on 2014/5/6
 */
public class RemoteServer {

    private SeleniumServer seleniumServer;

    public RemoteServer() throws Exception {
        this.seleniumServer=new SeleniumServer();
    }

    public void start() throws Exception {
        this.seleniumServer.boot();
        this.seleniumServer.start();
    }

    public void stop(){
        this.seleniumServer.stop();
    }

    public static void main(String[] args) throws Exception {
        RemoteServer server = new RemoteServer();
        server.start();
    }
}

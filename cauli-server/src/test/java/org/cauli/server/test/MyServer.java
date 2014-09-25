package org.cauli.server.test;

import org.cauli.server.CauliServer;
import org.cauli.server.Configuration;
import org.cauli.server.controller.Controller;
import org.cauli.server.router.Router;

/**
 * Created by tianqing.wang on 2014/9/25
 */
public class MyServer extends CauliServer{
    @Override
    public void configRoute(Router router) {
        router.add("/", HelloController.class);
    }

    @Override
    public void configServer(Configuration configuration) {
        configuration.setPort(10002);
    }

    public static void main(String[] args) throws Exception {
        new MyServer().start();
    }
}

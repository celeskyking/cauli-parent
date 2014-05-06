package org.cauli.server.test;

import org.cauli.server.*;

import java.util.concurrent.ExecutionException;

/**
 * Created by tianqing.wang on 2014/5/4
 */
public class ServerTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        WebServers.createWebServer(9999).add(new HttpHandler() {
            @Override
            public void handleHttpRequest(HttpRequest request, HttpResponse response, HttpControl control) throws Exception {
                response.content("hello,world").end();
            }
        }).start().get();
    }
}

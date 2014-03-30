package org.cauli.server.handler;

import org.cauli.server.EventSourceHandler;
import org.cauli.server.HttpControl;
import org.cauli.server.HttpHandler;
import org.cauli.server.HttpRequest;
import org.cauli.server.HttpResponse;

public class HttpToEventSourceHandler implements HttpHandler {
    private final EventSourceHandler handler;

    public HttpToEventSourceHandler(EventSourceHandler handler) {
        this.handler = handler;
    }

    @Override
    public void handleHttpRequest(HttpRequest request, HttpResponse response, HttpControl control) throws Exception {
        control.upgradeToEventSourceConnection(handler);
    }
}

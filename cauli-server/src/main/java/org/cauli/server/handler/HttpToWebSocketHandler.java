package org.cauli.server.handler;

import org.cauli.server.HttpControl;
import org.cauli.server.HttpHandler;
import org.cauli.server.HttpRequest;
import org.cauli.server.HttpResponse;
import org.cauli.server.WebSocketHandler;

public class HttpToWebSocketHandler implements HttpHandler {
    private final WebSocketHandler handler;

    public HttpToWebSocketHandler(WebSocketHandler handler) {
        this.handler = handler;
    }

    @Override
    public void handleHttpRequest(HttpRequest request, HttpResponse response, HttpControl control) throws Exception {
        control.upgradeToWebSocketConnection(handler);
    }
}

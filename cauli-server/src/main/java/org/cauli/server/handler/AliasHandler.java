package org.cauli.server.handler;

import org.cauli.server.HttpControl;
import org.cauli.server.HttpHandler;
import org.cauli.server.HttpRequest;
import org.cauli.server.HttpResponse;

public class AliasHandler implements HttpHandler {
    private final String uri;

    public AliasHandler(String uri) {
        this.uri = uri;
    }

    @Override
    public void handleHttpRequest(HttpRequest request, HttpResponse response, HttpControl control) throws Exception {
        request.uri(uri);
        control.nextHandler(request, response);
    }
}

package org.cauli.server;

public interface HttpHandler {
    public void handleHttpRequest(HttpRequest request, HttpResponse response, HttpControl control) throws Exception;
}

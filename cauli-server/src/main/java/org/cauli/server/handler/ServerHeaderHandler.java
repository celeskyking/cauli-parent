package org.cauli.server.handler;

import org.cauli.server.HttpControl;
import org.cauli.server.HttpHandler;
import org.cauli.server.HttpRequest;
import org.cauli.server.HttpResponse;

/**
 * Handler that sets the HTTP 'Server' response header.
 */
public class ServerHeaderHandler implements HttpHandler {

    private final String value;

    /**
     * Value to set for HTTP Server header, or null to ensure the header is blank.
     */
    public ServerHeaderHandler(String value) {
        this.value = value;
    }

    @Override
    public void handleHttpRequest(HttpRequest request, HttpResponse response, HttpControl control) throws Exception {
        response.header("Server", value);
        control.nextHandler();
    }
}

package org.cauli.server.handler;

import org.jboss.netty.handler.codec.http.HttpHeaders;
import org.cauli.server.HttpControl;
import org.cauli.server.HttpHandler;
import org.cauli.server.HttpRequest;
import org.cauli.server.HttpResponse;

import java.util.Date;

/**
 * Handler that sets the HTTP 'Server' response header.
 */
public class DateHeaderHandler implements HttpHandler {

    @Override
    public void handleHttpRequest(HttpRequest request, HttpResponse response, HttpControl control) throws Exception {
        if (!response.containsHeader(HttpHeaders.Names.DATE)) {
            response.header(HttpHeaders.Names.DATE, new Date());
        }
        control.nextHandler();
    }
}

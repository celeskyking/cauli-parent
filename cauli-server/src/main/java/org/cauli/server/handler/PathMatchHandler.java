package org.cauli.server.handler;

import org.cauli.server.HttpControl;
import org.cauli.server.HttpHandler;
import org.cauli.server.HttpRequest;
import org.cauli.server.HttpResponse;

import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PathMatchHandler implements HttpHandler {

    private final Pattern pathPattern;
    private final HttpHandler httpHandler;

    public PathMatchHandler(Pattern pathPattern, HttpHandler httpHandler) {
        this.pathPattern = pathPattern;
        this.httpHandler = httpHandler;
    }

    public PathMatchHandler(String path, HttpHandler httpHandler) {
        this(Pattern.compile(path), httpHandler);
    }

    public Boolean pathIsAMatch(HttpRequest request){
        try {
            String path = URI.create(request.uri()).getPath();
            Matcher matcher = pathPattern.matcher(path);
            if (matcher.matches()) {
                return true;
            }
        } catch (IllegalArgumentException e) {
        }
        return false;
    }

    @Override
    public void handleHttpRequest(HttpRequest request, HttpResponse response, HttpControl control) throws Exception {
        if (pathIsAMatch(request)) {
            httpHandler.handleHttpRequest(request, response, control);
        } else {
            control.nextHandler();
        }
    }
}

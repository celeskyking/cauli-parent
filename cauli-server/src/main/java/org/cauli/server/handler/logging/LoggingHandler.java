package org.cauli.server.handler.logging;

import org.cauli.server.EventSourceConnection;
import org.cauli.server.EventSourceHandler;
import org.cauli.server.HttpControl;
import org.cauli.server.HttpHandler;
import org.cauli.server.HttpRequest;
import org.cauli.server.HttpResponse;
import org.cauli.server.WebSocketConnection;
import org.cauli.server.WebSocketHandler;
import org.cauli.server.wrapper.HttpControlWrapper;
import org.cauli.server.wrapper.HttpResponseWrapper;

public class LoggingHandler implements HttpHandler {

    private final LogSink logSink;

    public LoggingHandler(LogSink logSink) {
        this.logSink = logSink;
    }

    public LogSink logSink() {
        return logSink;
    }

    @Override
    public void handleHttpRequest(final HttpRequest request, HttpResponse response, HttpControl control) throws Exception {
        logSink.httpStart(request);

        HttpResponseWrapper responseWrapper = new HttpResponseWrapper(response) {
            @Override
            public HttpResponseWrapper end() {
                logSink.httpEnd(request);
                return super.end();
            }

            @Override
            public HttpResponseWrapper error(Throwable error) {
                logSink.httpEnd(request);
                logSink.error(request, error);
                return super.error(error);
            }
        };

        HttpControlWrapper controlWrapper = new HttpControlWrapper(control) {

            private LoggingWebSocketConnection loggingWebSocketConnection;
            private LoggingEventSourceConnection loggingEventSourceConnection;

            @Override
            public WebSocketConnection webSocketConnection() {
                return loggingWebSocketConnection;
            }

            @Override
            public WebSocketConnection upgradeToWebSocketConnection(WebSocketHandler handler) {
                loggingWebSocketConnection = new LoggingWebSocketConnection(logSink, super.webSocketConnection());
                return super.upgradeToWebSocketConnection(new LoggingWebSocketHandler(logSink, loggingWebSocketConnection, handler));
            }

            @Override
            public EventSourceConnection eventSourceConnection() {
                return loggingEventSourceConnection;
            }

            @Override
            public EventSourceConnection upgradeToEventSourceConnection(EventSourceHandler handler) {
                loggingEventSourceConnection = new LoggingEventSourceConnection(logSink, super.eventSourceConnection());
                return super.upgradeToEventSourceConnection(new LoggingEventSourceHandler(logSink, loggingEventSourceConnection, handler));
            }
        };
        control.nextHandler(request, responseWrapper, controlWrapper);
    }


}

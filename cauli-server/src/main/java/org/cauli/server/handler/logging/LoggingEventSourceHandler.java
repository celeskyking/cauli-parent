package org.cauli.server.handler.logging;

import org.cauli.server.EventSourceConnection;
import org.cauli.server.EventSourceHandler;

class LoggingEventSourceHandler implements EventSourceHandler {

    private final LogSink logSink;
    private final EventSourceConnection loggingConnection;
    private final EventSourceHandler handler;

    LoggingEventSourceHandler(LogSink logSink, EventSourceConnection loggingConnection, EventSourceHandler handler) {
        this.logSink = logSink;
        this.loggingConnection = loggingConnection;
        this.handler = handler;
    }

    @Override
    public void onOpen(EventSourceConnection connection) throws Exception {
        logSink.eventSourceConnectionOpen(connection);
        handler.onOpen(loggingConnection);
    }

    @Override
    public void onClose(EventSourceConnection connection) throws Exception {
        logSink.eventSourceConnectionClose(connection);
        logSink.httpEnd(connection.httpRequest());
        handler.onClose(loggingConnection);
    }
}

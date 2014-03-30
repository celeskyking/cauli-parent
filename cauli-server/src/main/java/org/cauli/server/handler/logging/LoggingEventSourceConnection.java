package org.cauli.server.handler.logging;

import org.cauli.server.EventSourceConnection;
import org.cauli.server.EventSourceMessage;
import org.cauli.server.wrapper.EventSourceConnectionWrapper;

class LoggingEventSourceConnection extends EventSourceConnectionWrapper {

    private final LogSink logSink;

    LoggingEventSourceConnection(LogSink logSink, EventSourceConnection connection) {
        super(connection);
        this.logSink = logSink;
    }

    @Override
    public EventSourceConnectionWrapper send(EventSourceMessage message) {
        logSink.eventSourceOutboundData(this, message.build());
        return super.send(message);
    }
}

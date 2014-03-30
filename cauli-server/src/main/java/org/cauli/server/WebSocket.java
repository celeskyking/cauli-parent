package org.cauli.server;

public interface WebSocket extends Endpoint<WebSocket> {
    WebSocket reconnectEvery(long millis);
}

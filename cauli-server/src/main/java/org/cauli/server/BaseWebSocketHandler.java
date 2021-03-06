package org.cauli.server;

/**
 * Base implementation that does nothing, except for automatically calling
 * {@link org.cauli.server.WebSocketConnection#pong(byte[])} when {@link #onPing(org.cauli.server.WebSocketConnection, byte[])}
 * receives a ping.
 */
public class BaseWebSocketHandler implements WebSocketHandler {
    @Override
    public void onOpen(WebSocketConnection connection) throws Exception {
    }

    @Override
    public void onClose(WebSocketConnection connection) throws Exception {
    }

    @Override
    public void onMessage(WebSocketConnection connection, String msg) throws Throwable {
    }

    @Override
    public void onMessage(WebSocketConnection connection, byte[] msg) throws Throwable {
    }

    @Override
    public void onPing(WebSocketConnection connection, byte[] msg) throws Throwable {
        connection.pong(msg);
    }

    @Override
    public void onPong(WebSocketConnection connection, byte[] msg) throws Throwable {
    }
}

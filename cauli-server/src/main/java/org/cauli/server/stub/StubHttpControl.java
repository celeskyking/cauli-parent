package org.cauli.server.stub;

import org.cauli.server.EventSourceConnection;
import org.cauli.server.EventSourceHandler;
import org.cauli.server.HttpControl;
import org.cauli.server.HttpRequest;
import org.cauli.server.HttpResponse;
import org.cauli.server.WebSocketConnection;
import org.cauli.server.WebSocketHandler;

import java.util.concurrent.Executor;

public class StubHttpControl implements HttpControl {

    private HttpRequest request;
    private HttpResponse response;
    private WebSocketHandler webSocketHandler;
    private WebSocketConnection webSocketConnection;

    public StubHttpControl() {
    }

    public StubHttpControl(HttpRequest request, HttpResponse response) {
        this.request = request;
        this.response = response;
    }

    public StubHttpControl(WebSocketConnection connection) {
        this.webSocketConnection = connection;
    }

    public StubHttpControl(HttpRequest request, HttpResponse response, WebSocketConnection connection) {
        this.request = request;
        this.response = response;
        this.webSocketConnection = connection;
    }

    public HttpRequest request() {
        return request;
    }

    public HttpResponse response() {
        return response;
    }

    public StubHttpControl request(HttpRequest request) {
        this.request = request;
        return this;
    }

    public StubHttpControl response(HttpResponse response) {
        this.response = response;
        return this;
    }

    @Override
    public void nextHandler() {
        nextHandler(request, response, this);
    }

    @Override
    public void nextHandler(HttpRequest request, HttpResponse response) {
        nextHandler(request, response, this);
    }

    @Override
    public void nextHandler(HttpRequest request, HttpResponse response, HttpControl control) {
        response.status(404).end();
    }

    @Override
    public WebSocketConnection upgradeToWebSocketConnection(WebSocketHandler handler) {
        this.webSocketHandler = handler;
        return this.webSocketConnection;
    }

    @Override
    public WebSocketConnection webSocketConnection() {
        return this.webSocketConnection;
    }

    @Override
    public EventSourceConnection upgradeToEventSourceConnection(EventSourceHandler handler) {
        throw new UnsupportedOperationException();
//        this.webSocketHandler = handler;
//        return webSocketConnection;
    }

    @Override
    public EventSourceConnection eventSourceConnection() {
        throw new UnsupportedOperationException();
//        return this.webSocketConnection;
    }

    public StubHttpControl webSocketConnection(WebSocketConnection connection) {
        this.webSocketConnection = connection;
        return this;
    }

    public WebSocketHandler webSocketHandler() {
        return webSocketHandler;
    }

    @Override
    public Executor handlerExecutor() {
        return this;
    }

    @Override
    public void execute(Runnable command) {
        command.run();
    }
}

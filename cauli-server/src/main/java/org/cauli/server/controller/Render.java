package org.cauli.server.controller;

import org.cauli.server.HttpRequest;
import org.cauli.server.HttpResponse;

import java.io.IOException;

/**
 * Created by tianqing.wang on 2014/9/1
 */
public abstract class Render{

    protected String view;
    protected transient HttpRequest request;
    protected transient HttpResponse response;




    public final Render setContext(HttpRequest request, HttpResponse response) {
        this.request = request;
        this.response = response;
        return this;
    }

    public final Render setContext(HttpRequest request, HttpResponse response, String viewPath) {
        this.request = request;
        this.response = response;
        if (view != null && !view.startsWith("/"))
            view = viewPath + view;
        return this;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    /**
     * Render to client
     */
    public abstract void render() throws IOException;
}
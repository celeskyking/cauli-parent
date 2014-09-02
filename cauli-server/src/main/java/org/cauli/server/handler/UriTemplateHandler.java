package org.cauli.server.handler;

import org.cauli.server.HttpControl;
import org.cauli.server.HttpHandler;
import org.cauli.server.HttpRequest;
import org.cauli.server.HttpResponse;
import org.springframework.web.util.UriTemplate;

import java.net.URI;

/**
 * Created by tianqing.wang on 2014/9/1
 */
public class UriTemplateHandler implements HttpHandler{

    private String uriTemplate;

    private HttpHandler httpHandler;

    public UriTemplateHandler(String uriTemplate,HttpHandler httpHandler){
        this.uriTemplate=uriTemplate;
        this.httpHandler=httpHandler;
    }

    @Override
    public void handleHttpRequest(HttpRequest request, HttpResponse response, HttpControl control) throws Exception {
        String uri = URI.create(request.uri()).getPath();
        if(new UriTemplate(uriTemplate).matches(uri)){
            this.httpHandler.handleHttpRequest(request,response,control);
        }else{
            control.nextHandler();
        }
    }
}

package org.cauli.mock.sender.impl;

import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import org.cauli.mock.response.Response;
import org.cauli.mock.entity.KeyValueStores;
import org.cauli.mock.sender.ISender;
import org.cauli.server.HttpMethod;

import java.lang.reflect.Method;

/**
 * @auther sky
 */
public class HttpSender implements ISender{

    private String url;

    private HttpMethod method;

    public HttpSender(String url){
        this.url=url;
        this.method=HttpMethod.GET;
    }

    public HttpSender(String url,HttpMethod method){
        this.url=url;
        this.method=method;
    }

    @Override
    public Response sender(String context) {
        HttpRequest request = getRequest(method,url).queryString(context);
        HttpResponse response = request.send();

        return null;
    }

    @Override
    public Response sender(KeyValueStores keyValueStores) {
        return null;
    }



    private HttpRequest getRequest(HttpMethod method,String url){
        try {
            Method clazzMethod = HttpRequest.class.getDeclaredMethod(String.valueOf(method).toLowerCase(),String.class);
            return (HttpRequest) clazzMethod.invoke(url);
        } catch (Exception e) {
            throw new RuntimeException("未找到定义的HTTP的请求方法");
        }

    }

}

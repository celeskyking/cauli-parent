package org.cauli.mock.sender;

import com.google.common.collect.Maps;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import org.apache.commons.lang3.StringUtils;
import org.cauli.server.HttpMethod;


import java.util.Map;

/**
 * @auther sky
 */
public class HttpSender{

    private Map<String,String> headers = Maps.newHashMap();

    private Map<String,String> query  = Maps.newHashMap();

    private Map<String,Object> form = Maps.newHashMap();

    private String queryString;

    private HttpMethod method;

    private String body;

    private String queryEncoding;

    private String formEncoding;

    private String url;

    public HttpSender method(HttpMethod method){
        this.method=method;
        return this;
    }

    public HttpSender url(String url){
        this.url=url;
        return this;
    }


    public HttpSender header(Map<String,String> map){
        this.headers.putAll(map);
        return this;
    }

    public HttpSender body(String text){
        this.body=text;
        return this;
    }

    public HttpSender header(String key,String value){
        this.headers.put(key,value);
        return this;
    }

    public HttpSender query(String key,String value){
        this.query.put(key,value);
        return this;
    }

    public HttpSender query(Map<String,String> queryMap){
        this.query.putAll(queryMap);
        return this;
    }

    public HttpSender queryString(String content){
        this.queryString=content;
        return this;
    }

    public HttpSender form(String key,Object value){
        this.form.put(key,value);
        return this;
    }

    public HttpSender form(Map<String,Object> form){
        this.form.putAll(form);
        return this;
    }

    public HttpSender queryEncoding(String encoding){
        this.queryEncoding=encoding;
        return this;
    }

    public HttpSender formEncoding(String encoding){
        this.formEncoding=encoding;
        return this;
    }



    public HttpResponse send(){
        HttpRequest request=new HttpRequest().method(method.toString()).set(url);
        if(query!=null&&query.size()>1){
            request.query(query);
        }
        if(headers!=null&&headers.size()>1){
            for(Map.Entry<String,String> entry: headers.entrySet()){
                request.header(entry.getKey(),entry.getValue());
            }
        }
        if(form!=null&&headers.size()>1){
            request.form(form);
        }
        if(StringUtils.isNotEmpty(queryString)){
            request.queryString(queryString);
        }
        if(StringUtils.isNotEmpty(queryEncoding)){
            request.queryEncoding(queryEncoding);
        }
        if(StringUtils.isNotEmpty(body)){
            request.bodyText(body);
        }
        if(StringUtils.isNotEmpty(formEncoding)){
            request.formEncoding(formEncoding);
        }
        return request.send();
    }




}

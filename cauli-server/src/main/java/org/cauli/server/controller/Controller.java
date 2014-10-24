package org.cauli.server.controller;


import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import jodd.util.StringUtil;
import org.cauli.server.HttpRequest;
import org.cauli.server.HttpResponse;
import org.cauli.server.action.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.UriTemplate;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.HttpCookie;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by tianqing.wang on 2014/9/1
 */
public  class Controller {


    private Logger logger = LoggerFactory.getLogger(Controller.class);

    private String rootPath;

    private Map<String,Action> actionMap = Maps.newConcurrentMap();


    private Map<String,String> pathParams= Maps.newHashMap();


    private HttpRequest request;

    private HttpResponse response;


    public  void before(){};

    public  void after(){};

    public HttpRequest request(){
        return request;
    }

    public Controller charset(Charset charset){
        response.charset(charset);
        return this;
    }
    public String body(){
        return request.body();
    }


    public HttpResponse response(){
        return response;
    }

    public String pathParam(String name){
        return pathParams.get(name);
    }

    public String queryParam(String name){
        return request.queryParam(name);
    }

    public java.util.Set<String> queryKeys(){
        return request.postParamKeys();
    }

    public String postParam(String name){
        return request.postParam(name);
    }

    public Set<String> postKeys(){
        return request.postParamKeys();
    }

    public List<String> queryParams(String name){
        return request.queryParams(name);
    }

    public List<String> postParams(String name){
        return request.postParams(name);
    }

    public String header(String name){
        return request.header(name);
    }

    public Controller header(String name,String value){
        response.header(name,value);
        return this;
    }

    public Controller header(String name,Date date){
        response.header(name,date);
        return this;
    }

    public String cookie(String name){
        return request.cookieValue(name);
    }

    public List<HttpCookie> cookies(){
        return request.cookies();
    }

    public Controller cookie(String name,String value){
        response.cookie(new HttpCookie(name,value));
        return this;
    }

    public Controller cookie(HttpCookie cookie){
        response.cookie(cookie);
        return this;
    }

    public List<Map.Entry<String, String>> headers(String name,int valeu){
        return request.allHeaders();
    }

    public Controller header(String name,Long num){
        response.header(name,num);
        return this;
    }

    public void renderText(String content){
        if(StringUtil.isNotEmpty(content)){
            response.content(content).end();
        }else{
            throw new RuntimeException("renderText()响应内容为空");

        }

    }

    public void renderHtml(String html){
        if(StringUtil.isNotEmpty(html)){
            response.content(html).end();
        }else{
            throw new RuntimeException("renderHtml()响应内容为空");
        }

    }


    public Controller produces(String produce){
        response.header("Content-Type",produce);
        return this;
    }

    public Controller data(String key,Object object){
        request.data(key,object);
        return this;
    }

    public void renderJson(Object model){
        if(model==null){
            throw new RuntimeException("renderJson()响应内容为空");
        }
        if(model instanceof String){
            try{
                Object jsonObject=JSON.parse((String) model);
                if(jsonObject==null){
                    response.status(500).end();
                    return;
                }else{
                    response.content((String) model).end();
                    return;
                }
            }catch (Exception e){
                logger.error("json解析错误",e);
                response.status(500).end();
                return;
            }

        }
        response.content(JSON.toJSONString(model)).end();
    }

    public void renderFreemarker(String view)throws Exception{
        try {
            FreemarkerRender render = FreemarkerRender.getInstance();
            render.setContext(request,response);
            render.setView(view);
            render.render();
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public void renderView(){
        throw new IllegalArgumentException("Not Support this Method:RenderView()");
    }

    public void redirect(String uri){
        response.header("Location", uri).status(302).end();
    }

    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    public void parseAction(){
        Method[] methods=this.getClass().getDeclaredMethods();
        for(Method method:methods){
            if(method.getReturnType()==Void.TYPE&&Modifier.isPublic(method.getModifiers())){
                Action action = new Action(method,this);
                actionMap.put(action.getUriTemplate(),action);
            }
        }
    }


    public Map<String,Action> getActions(){
        return actionMap;
    }

    public Action getMatchAction(String uri){
        for(Map.Entry<String,Action> entry:actionMap.entrySet()){
            UriTemplate uriTemplate= new UriTemplate(entry.getKey());
            if(uriTemplate.matches(uri)){
                pathParams=uriTemplate.match(uri);
                return entry.getValue();
            }
        }
        return null;
    }

    public HttpRequest getRequest() {
        return request;
    }

    public void setRequest(HttpRequest request) {
        this.request = request;
    }

    public HttpResponse getResponse() {
        return response;
    }

    public void setResponse(HttpResponse response) {
        this.response = response;
    }


}

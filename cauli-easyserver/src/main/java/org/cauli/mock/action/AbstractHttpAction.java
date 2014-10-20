package org.cauli.mock.action;

import com.google.common.collect.Maps;
import org.cauli.mock.entity.ActionInfo;
import org.cauli.mock.entity.RequestHistory;
import org.cauli.mock.sender.HttpSender;
import org.cauli.mock.util.XMLUtil;
import org.cauli.server.HttpMethod;
import org.cauli.server.HttpRequest;
import org.cauli.server.HttpResponse;
import org.dom4j.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 * Created by tianqing.wang on 2014/8/14
 */
public abstract class AbstractHttpAction extends AbstractAction<HttpRequest,String>{

    private Logger logger = LoggerFactory.getLogger(AbstractHttpAction.class);

    private HttpResponse response;

    public AbstractHttpAction(String name) {
        super(name);
    }


    public abstract void config(ActionInfo actionInfo);


    public Map<String,String> requestMapWithSingleValue(){
        Map<String,String> requestMap = Maps.newHashMap();
        if(request().method().equalsIgnoreCase("GET")){
            for(String key:request().queryParamKeys()){
                requestMap.put(key,request().queryParam(key));
            }
        }else{
            for(String key:request().postParamKeys()){
                requestMap.put(key,request().postParam(key));
            }
        }
        return requestMap;
    }

    public Map<String,String[]> requestMap(){
        Map<String,String[]> requestMap = Maps.newHashMap();
        if(request().method().equalsIgnoreCase("GET")){
            for(String key:request().queryParamKeys()){
                String[] strings = new String[request().queryParamKeys().size()];
                requestMap.put(key,request().queryParams(key).toArray(strings));
            }
        }else{
            for(String key:request().postParamKeys()){
                String[] strings = new String[request().postParamKeys().size()];
                requestMap.put(key,request().postParams(key).toArray(strings));
            }
        }
        return requestMap;
    }


    public HttpSender sender(){
        HttpMethod method = getActionInfo().getCallbackInfo().http.getMethod();
        String url = getActionInfo().getCallbackInfo().http.getUrl();
        Charset queryEncoding = getActionInfo().getCallbackInfo().http.getQueryEncoding();
        Charset formEncoding = getActionInfo().getCallbackInfo().http.getFormEncoding();
        HttpSender sender = new HttpSender().method(method).url(url);
        if(queryEncoding!=null){
            sender.queryEncoding(queryEncoding.displayName());
        }
        if(formEncoding!=null){
            sender.formEncoding(formEncoding.displayName());
        }
        return sender;
    }


    public Document requestToXml() {
        String text = request().body();
        if (XMLUtil.isXML(text)) {
            return XMLUtil.load(request().body());
        } else {
            throw new RuntimeException("the body of request is not format for xml");
        }
    }

    public String xmlValue(String xpath){
        try{
            return XMLUtil.getStringValue(requestToXml(),xpath);
        }catch (Exception e){
            logger.error("未找到{}位置的xml元素",xpath);
            return null;
        }

    }


    public HttpResponse response() {
        return response;
    }

    public void setResponse(HttpResponse response) {
        this.response = response;
    }


    @Override
    public String getRequestHistory(String date) {
        HttpRequest request = (HttpRequest) getParametersModel().getContext().getContext("_request");
        RequestHistory.Http http = new RequestHistory.Http();
        http.setBody(request.body());
        http.setHeaders(parseHeader(request.allHeaders()));
        http.setPostParameters(parseRequestPost(request));
        http.setQueryParameters(parseRequestQuery(request));
        return http.toString();
    }

    private Map<String,String> parseHeader(List<Map.Entry<String,String>> headers){
        Map<String,String> headerMap = Maps.newHashMap();
        for(Map.Entry<String,String> entry:headers){
            headerMap.put(entry.getKey(),entry.getValue());
        }
        return headerMap;
    }

    private Map<String,String> parseRequestQuery(HttpRequest request){
        Map<String,String> requestMap = Maps.newHashMap();
        if(request.method().equalsIgnoreCase("GET")){
            for(String key:request().queryParamKeys()){
                requestMap.put(key,request().queryParam(key));
            }
        }
        return requestMap;
    }

    private Map<String,String> parseRequestPost(HttpRequest request){
        Map<String,String> requestMap = Maps.newHashMap();
        if(request.method().equalsIgnoreCase("POST")){
            for(String key:request().postParamKeys()){
                requestMap.put(key,request().postParam(key));
            }
        }
        return requestMap;
    }



}

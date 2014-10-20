package org.cauli.mock.entity;


import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;

import java.io.Serializable;
import java.util.Map;
/**
 * Created by tianqing.wang on 2014/10/20
 */
public class RequestHistory implements Serializable{

    public static class Http{
        private String body;

        private Map<String,String> headers;

        private Map<String,String> queryParameters;

        private Map<String,String> postParameters;

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public Map<String, String> getHeaders() {
            return headers;
        }

        public void setHeaders(Map<String, String> headers) {
            this.headers = headers;
        }

        public Map<String, String> getQueryParameters() {
            return queryParameters;
        }

        public void setQueryParameters(Map<String, String> queryParameters) {
            this.queryParameters = queryParameters;
        }

        public Map<String, String> getPostParameters() {
            return postParameters;
        }

        public void setPostParameters(Map<String, String> postParameters) {
            this.postParameters = postParameters;
        }

        public String toString(){
            Map<String,String> map = Maps.newHashMap();
            map.put("header", JSON.toJSONString(headers));
            map.put("body",body);
            map.put("query",JSON.toJSONString(queryParameters));
            map.put("form",JSON.toJSONString(postParameters));
            return JSON.toJSONString(map);
        }
    }


    public static class Socket{
        String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String toString(){
            Map<String,String> map = Maps.newHashMap();
            map.put("content", content);
            return JSON.toJSONString(map);
        }
    }


}

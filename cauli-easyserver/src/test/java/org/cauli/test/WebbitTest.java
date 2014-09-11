package org.cauli.test;

import jodd.util.StringUtil;
import org.cauli.server.*;

/**
 * Created by tianqing.wang on 2014/8/25
 */
public class WebbitTest {

    public static void main(String[] args) throws Exception {
        WebServers.createWebServer(9999).add(new HttpHandler() {
            @Override
            public void handleHttpRequest(HttpRequest request, HttpResponse response, HttpControl httpControl) throws Exception {
                System.out.println(request.method());
                for(String key:request.queryParamKeys()){
                    System.out.println(key+"-----"+request.queryParam(key));
                }
                for(String key:request.postParamKeys()){
                    System.out.println(key+"++++++"+request.postParam(key));
                }
                String text = request.postParam("user");
                if(StringUtil.isNotEmpty(text)){
                    response.content(request.postParam("user")).end();
                }else{
                    response.content("hello").end();
                }

            }
        }).start().get();
    }
}

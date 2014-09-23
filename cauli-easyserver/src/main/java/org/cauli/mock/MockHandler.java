package org.cauli.mock;

import org.apache.commons.lang3.StringUtils;
import org.cauli.mock.action.AbstractHttpAction;
import org.cauli.mock.core.convert.ConvertExecuter;
import org.cauli.mock.core.convert.ConvertManager;
import org.cauli.mock.entity.ParametersModel;
import org.cauli.mock.server.AbstractHttpServer;
import org.cauli.mock.server.MockServer;
import org.cauli.server.HttpControl;
import org.cauli.server.HttpHandler;
import org.cauli.server.HttpRequest;
import org.cauli.server.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @auther sky
 */
public class MockHandler implements HttpHandler {

    private AbstractHttpServer server;


    public MockServer getServer() {
        return server;
    }

    public void setServer(AbstractHttpServer server) {
        this.server = server;
    }

    private Logger logger = LoggerFactory.getLogger(MockHandler.class);
    @Override
    public void handleHttpRequest(final HttpRequest request, final HttpResponse httpResponse, HttpControl httpControl) throws Exception {
        dump(request);
        logger.info("请求的uri:{},ServerName:{}",request.uri(),this.server.getServerName());
        ConvertManager.ConvertMap convertMap = new ConvertManager.ConvertMap();
        try{
            final ParametersModel parametersModel = new ParametersModel(convertMap);
            convertMap.register(ParametersModel.class,new ConvertExecuter() {
                @Override
                public Object execute(Object clazz, ParametersModel parameterValuePairs) {
                    return parametersModel;
                }
            });
            convertMap.register(HttpRequest.class,new ConvertExecuter() {
                @Override
                public Object execute(Object clazz, ParametersModel parameterValuePairs) {
                    parametersModel.getContext().addObject("request",request);
                    return request;
                }
            });
            convertMap.register(HttpResponse.class,new ConvertExecuter() {
                @Override
                public Object execute(Object clazz, ParametersModel parameterValuePairs) {
                    parametersModel.getContext().addObject("response",httpResponse);
                    return httpResponse;
                }
            });
            if(StringUtils.substringBefore(request.uri(),"?").contains("favicon.ico")) {
                httpResponse.status(404).end();
                return;
            }
            parseMock(request,httpResponse,parametersModel);
        }catch (Exception e){
            logger.error("请求处理中出现了错误",e);
            httpResponse.status(500).end();
        }
    }



    private void dump(HttpRequest request){
        logger.info("请求的参数方法为:{}",request.method());
        logger.info("请求的body:{}",request.body());
        if(request.method().equalsIgnoreCase("get")){
            for(String key:request.queryParamKeys()){
                logger.info("key:{}, value:{}",key,request.queryParam(key));
            }
        }else{
            for(String key:request.postParamKeys()){
                logger.info("key:{}, value:{}",key,request.postParam(key));
            }
        }
    }

    private void parseMock(HttpRequest request,HttpResponse httpResponse, final ParametersModel parametersModel) throws Exception {
        String uri = StringUtils.substringBefore(request.uri(),"?");
        final AbstractHttpAction action = (AbstractHttpAction) server.getAction(request,parametersModel);
        if(action==null){
            logger.error("not found action,uri:{}",uri);
            httpResponse.status(404).end();
            return;
        }
        action.setRequest(request);
        action.setResponse(httpResponse);
        String responseContent =  action.build();
        logger.info("响应内容为:{}",responseContent);
        httpResponse.content(responseContent).end();
        if(action.getActionInfo().isUseMessage()){
            new Runnable() {
                @Override
                public void run() {
                    action.onMessage(parametersModel);
                }
            }.run();
        }

        return;
    }


}

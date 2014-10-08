package org.cauli.mock.admin.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import jodd.typeconverter.TypeConverter;
import jodd.typeconverter.TypeConverterManager;
import org.apache.commons.lang3.StringUtils;
import org.cauli.common.keyvalue.KeyValueStore;
import org.cauli.common.keyvalue.KeyValueStores;
import org.cauli.mock.ServerBuilder;
import org.cauli.mock.ServerInitStatus;
import org.cauli.mock.ServerProtocol;
import org.cauli.mock.ServerStyle;
import org.cauli.mock.action.MockAction;
import org.cauli.mock.admin.AdminService;
import org.cauli.mock.entity.ActionInfo;
import org.cauli.mock.entity.CallbackResponse;
import org.cauli.mock.entity.DefaultResponse;
import org.cauli.mock.entity.ServerInfo;
import org.cauli.mock.exception.ActionExecuteException;
import org.cauli.mock.server.MockServer;
import org.cauli.mock.server.ServerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.security.validator.KeyStores;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @auther sky
 */
public class AdminServiceImpl implements AdminService {

    private Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

    @Override
    public String getServerInfo(String serverName) {
        logger.info("server的信息:serverName:{}",serverName);
        ServerInfo info= ServerBuilder.getInstance().getServer(serverName).getServerInfo();
        KeyValueStores stores = getServerKeyValueStore(info);
        String result;
        if(info!=null){
            result= stores.toJSON();
            logger.info("获取的server[{}]信息:{}",serverName,result);
            return result;
        }
        return errorMsg();
    }

    @Override
    public String getActionInfo(String serverName, String actionName) {
        logger.info("action的信息:serverName:{},actionName:{}",serverName,actionName);
        MockServer server=ServerBuilder.getInstance().getServer(serverName);
        if(server==null){
            return serverNotFountErrorMsg(serverName);
        }
        MockAction action = server.getAction(actionName);
        if(action==null){
            return actionNotFountErrorMsg(serverName,actionName);
        }
        KeyValueStores stores = getActionKeyValueStore(server,action);
        String result=stores.toJSON();
        logger.info("获取的server:{},action:{}的信息:{}",serverName,actionName,result);
        return result;
    }

    @Override
    public String getServers() {
        logger.info("获取所有Server信息...");
        Set<ServerInfo> infos =ServerBuilder.getInstance().getServerInfos();
        List<Map<String,Object>> list = Lists.newArrayList();
        for(ServerInfo info:infos){
            list.add(getServerKeyValueStore(info).toMap());
        }
        logger.info("所有Server信息:" + JSON.toJSONString(list));
        return JSON.toJSONString(list);
    }

    @Override
    public String getActionsOfServer(String serverName) {
        logger.info("获取Server:{}的所有Actions",serverName);
        MockServer<MockAction> server = ServerBuilder.getInstance().getServer(serverName);
        if(server==null){
            return serverNotFountErrorMsg(serverName);
        }
        List<Map<String,Object>> infos = Lists.newArrayList();
        for(MockAction action:server.getActions()){
            infos.add(getActionKeyValueStore(server,action).toMap());
        }
        logger.info("Server[{}]:Actions:[{}]",serverName,infos);
        return JSON.toJSONString(infos);
    }

    @Override
    public String getTemplateValue(String serverName, String actionName, String returnStatus) {
        logger.info("获取模板信息:serverName:{},actionName:{],returnStatus:{}",serverName,actionName,returnStatus);
        MockAction action = getAction(serverName,actionName);
        if(action==null){
            return actionNotFountErrorMsg(serverName,actionName);
        }
        String value =  action.getTemplateValue(returnStatus);
        if(StringUtils.isEmpty(value)){
            logger.warn("模板内容为空");
        }else{
            logger.info("模板内容为:{}",value);
        }
        return value;
    }

    @Override
    public String getActionRetureStatus(String serverName, String actionName) {
        logger.info("获取Action的模板Status:serverName:{},actionName:{}",serverName,actionName);
        MockAction action = getAction(serverName,actionName);
        if(action==null){
            return actionNotFountErrorMsg(serverName,actionName);
        }
        String content =JSON.toJSONString(action.getTemplateStatuses().keySet());
        logger.info("获取的status:{}",content);
        return content;
    }

    @Override
    public String createServer(ServerStyle serverStyle, String serverName ,int port,ServerInitStatus initStatus) {
        logger.info("Service:createServer: serverStyle:{},serverName:{},port:{},initStatus:{}",serverStyle,serverName,port,initStatus);
        MockServer server = ServerFactory.create(serverStyle,serverName,port);
        ServerBuilder.getInstance().addServer(serverName,server);
        if(initStatus== ServerInitStatus.START){
            logger.info("创建Server:{},初始化状态为启动",serverName);
            try {
                server.start();
            } catch (Exception e) {
                logger.error("启动Server失败:{}",serverName,e);
                return serverNotFountErrorMsg(serverName);
            }
        }
        return successMsg();
    }

    @Override
    public String createActionOfServer(String serverName, String actionName, String path, String status, String temlateValue) throws Exception {
        logger.info("创建Action:serverName:{},actionName:{},path:{},status:{},temlateValue:{}",serverName,actionName,path,status,temlateValue);
        MockServer server = ServerBuilder.getInstance().getServer(serverName);
        server.createAction(actionName,path,status,temlateValue);
        server.loadActions();
        server.restart();
        return successMsg();
    }

    @Override
    public String createActionOfServer(String serverName, String actionName,String path,String templateValue) throws Exception {
        logger.info("createActionOfServer:serverName:{},actionName:{},path:{},templateValue:{}",serverName,actionName,path,templateValue);
        MockServer server = ServerBuilder.getInstance().getServer(serverName);
        if(server.getProtocol()== ServerProtocol.HTTP){
            if(!path.startsWith("/")){
                return pathErrorMsg();
            }
        }
        server.createAction(actionName,path,templateValue);
        logger.info("创建Action成功:{}",actionName);
        return successMsg();
    }

    @Override
    public String createTemplate(String ServerName, String actionName, String returnStatus,String templateValue) {
        logger.info("创建模板:ServerName:{},actionName:{},returnStatus:{},templateValue:{}",ServerName,actionName,returnStatus,templateValue);
        MockAction action = getAction(ServerName,actionName);
        action.addTemplate(returnStatus,templateValue);
        return successMsg();
    }

    @Override
    public String startServer(String serverName) {
        MockServer server = ServerBuilder.getInstance().getServer(serverName);
        if(server==null){
            return serverNotFountErrorMsg(serverName);
        }
        try {
            server.start();
        } catch (Exception e) {
            return serverStartErrorMsg();
        }
        return successMsg();
    }

    @Override
    public String stopServer(String serverName) {
        MockServer server = ServerBuilder.getInstance().getServer(serverName);
        if(server==null){
            return serverNotFountErrorMsg(serverName);
        }
        server.stop();
        return successMsg();
    }

    @Override
    public String restartServer(String serverName) {
        startServer(serverName);
        stopServer(serverName);
        return successMsg();
    }

    @Override
    public String updateActionRetureStatus(String serverName, String actionName, String returnStatus) {
        MockServer server = ServerBuilder.getInstance().getServer(serverName);
        if(server==null){
            return serverNotFountErrorMsg(serverName);
        }
        MockAction action = server.getAction(actionName);
        if(action==null){
            return actionNotFountErrorMsg(serverName,actionName);
        }
        action.getActionInfo().setReturnStatus(returnStatus);
        return successMsg();
    }

    @Override
    public String updateActionTemplateValue(String serverName, String actionName, String returnStatus, String templateValue) {
        MockServer server = ServerBuilder.getInstance().getServer(serverName);
        if(server==null){
            return serverNotFountErrorMsg(serverName);
        }
        MockAction action = server.getAction(actionName);
        if(action==null){
            return actionNotFountErrorMsg(serverName,actionName);
        }
        action.updateTemplateValue(returnStatus,templateValue);
        return successMsg();
    }

    @Override
    public String updateActionTimeOutMS(String serverName, String actionName, long timeoutMS) {
        MockServer server = ServerBuilder.getInstance().getServer(serverName);
        if(server==null){
            return serverNotFountErrorMsg(serverName);
        }
        MockAction action = server.getAction(actionName);
        if(action==null){
            return actionNotFountErrorMsg(serverName,actionName);
        }
        action.getActionInfo().setTimeoutMS(timeoutMS);
        return successMsg();
    }

    @Override
    public String updateActionIsUseTemplate(String serverName, String actionName, boolean isUseTemplate) {
        MockServer server = ServerBuilder.getInstance().getServer(serverName);
        if(server==null){
            return serverNotFountErrorMsg(serverName);
        }
        MockAction action = server.getAction(actionName);
        if(action==null){
            return actionNotFountErrorMsg(serverName,actionName);
        }
        action.getActionInfo().setUseTemplate(isUseTemplate);
        return successMsg();
    }

    @Override
    public String updateActionIsUseMessage(String serverName, String actionName, boolean isUseMessage) {
        MockServer server = ServerBuilder.getInstance().getServer(serverName);
        if(server==null){
            return serverNotFountErrorMsg(serverName);
        }
        MockAction action = server.getAction(actionName);
        if(action==null){
            return actionNotFountErrorMsg(serverName,actionName);
        }
        action.getActionInfo().setUseMessage(isUseMessage);
        return successMsg();
    }

    @Override
    public String updateActionTemplateEncoding(String serverName, String actionName, String templateEncoding) {
        MockServer server = ServerBuilder.getInstance().getServer(serverName);
        if(server==null){
            return serverNotFountErrorMsg(serverName);
        }
        MockAction action = server.getAction(actionName);
        if(action==null){
            return actionNotFountErrorMsg(serverName,actionName);
        }
        action.getActionInfo().setTemplateEncoding(templateEncoding);
        return successMsg();
    }

    @Override
    public String doCallback(String serverName, String actionName) {
        MockServer server = ServerBuilder.getInstance().getServer(serverName);
        if(server==null){
            return serverNotFountErrorMsg(serverName);
        }
        MockAction action = server.getAction(actionName);
        if(action==null){
            return actionNotFountErrorMsg(serverName,actionName);
        }
        Object result;
        try {
            result=action.callback();
        } catch (ActionExecuteException e) {
            return errorCallbackResult();
        }
        return successCallbackResult(result);
    }

    @Override
    public String updateActionCallBackUrl(String serverName, String actionName, String callbackUrl) {
        MockServer server = ServerBuilder.getInstance().getServer(serverName);
        if(server==null){
            return serverNotFountErrorMsg(serverName);
        }
        MockAction action = server.getAction(actionName);
        if(action==null){
            return actionNotFountErrorMsg(serverName,actionName);
        }
        if(server.getServerInfo().getProtocol()==ServerProtocol.HTTP){
            action.getActionInfo().getCallbackInfo().http.setUrl(callbackUrl);
        }else if(server.getServerInfo().getProtocol()==ServerProtocol.SOCKET){
            String host = StringUtils.substringBefore(callbackUrl,":");
            int port = Integer.parseInt(StringUtils.substringAfter(callbackUrl, ":"));
            action.getActionInfo().getCallbackInfo().socket.setHost(host);
            action.getActionInfo().getCallbackInfo().socket.setPort(port);
        }
        return successMsg();
    }

    @Override
    public String updateActionCallBackReturnStatus(String serverName, String actionName, String callbackReturnStatus) {
        MockServer server = ServerBuilder.getInstance().getServer(serverName);
        if(server==null){
            return serverNotFountErrorMsg(serverName);
        }
        MockAction action = server.getAction(actionName);
        if(action==null){
            return actionNotFountErrorMsg(serverName,actionName);
        }
        action.getActionInfo().getCallbackInfo().setReturnStatus(callbackReturnStatus);
        return successMsg();
    }


    private String errorMsg(){
        DefaultResponse response= new DefaultResponse();
        response.setErrorCode(3);
        response.setErrorInfo("实体转化为空");
        return JSON.toJSONString(response);
    }

    private String pathErrorMsg(){
        DefaultResponse response= new DefaultResponse();
        response.setErrorCode(7);
        response.setErrorInfo("path值不正确,必须以/开头");
        return JSON.toJSONString(response);
    }

    private String serverNotFountErrorMsg(String serverName){
        DefaultResponse response = new DefaultResponse();
        response.setErrorCode(4);
        response.setErrorInfo("未发现Server:"+serverName);
        return JSON.toJSONString(response);
    }

    private String actionNotFountErrorMsg(String serverName,String actionName){
        DefaultResponse response = new DefaultResponse();
        response.setErrorCode(4);
        response.setErrorInfo("未发现Server["+serverName+"]的Action:"+actionName);
        return JSON.toJSONString(response);
    }



    private MockAction getAction(String serverName,String action){
        return ServerBuilder.getInstance().getServer(serverName).getAction(action);
    }

    private String successMsg(){
        DefaultResponse response = new DefaultResponse();
        response.setErrorCode(0);
        response.setErrorInfo("请求成功");
        return JSON.toJSONString(response);
    }

    private String serverStartErrorMsg(){
        DefaultResponse response = new DefaultResponse();
        response.setErrorCode(6);
        response.setErrorInfo("服务器启动失败,可能端口被占用");
        return JSON.toJSONString(response);
    }

    private String successCallbackResult(Object result){
        CallbackResponse response = new CallbackResponse();
        response.setErrorInfo("异步通知调用成功");
        response.setErrorCode(0);
        if(result instanceof String){
            response.setResult((String) result);
        }else{
            response.setResult(JSON.toJSONString(result));
        }
        return JSON.toJSONString(response);
    }

    private String errorCallbackResult(){
        CallbackResponse response = new CallbackResponse();
        response.setErrorInfo("异步通知调用失败");
        response.setErrorCode(8);
        response.setResult(null);
        return JSON.toJSONString(response);
    }

    private KeyValueStores getServerKeyValueStore(ServerInfo info){
        KeyValueStores stores = new KeyValueStores();
        stores.add(new KeyValueStore("serverName",info.getServerName()));
        stores.add(new KeyValueStore("port",info.getPort()));
        if(info.getProtocol()==ServerProtocol.SOCKET){
            stores.add(new KeyValueStore("isAync",info.isAsyn()));
            stores.add(new KeyValueStore("requestEncoding",info.getRequestEncoding()));
            stores.add(new KeyValueStore("responseEncoding",info.getResponseEncoding()));
        }
        stores.add(new KeyValueStore("protocol",info.getProtocol()));
        stores.add(new KeyValueStore("status",info.getStatus()));
        stores.add(new KeyValueStore("initStatus",info.getInitStatus()));

        return stores;
    }


    private KeyValueStores getActionKeyValueStore(MockServer server,MockAction action){
        KeyValueStores stores = new KeyValueStores();
        stores.add(new KeyValueStore("actionName",action.getActionInfo().getActionName()));
        stores.add(new KeyValueStore("templateEncoding",action.getActionInfo().getTemplateEncoding()));
        stores.add(new KeyValueStore("returnStatus",action.getActionInfo().getReturnStatus()));
        if(server.getProtocol()==ServerProtocol.HTTP){
            stores.add(new KeyValueStore("requestUri",action.getActionInfo().getRequestUri()));
            stores.add(new KeyValueStore("callbackURL",action.getActionInfo().getCallbackInfo().http.getUrl()));
        }else if(server.getProtocol()==ServerProtocol.SOCKET){
            stores.add(new KeyValueStore("callbackHost",action.getActionInfo().getCallbackInfo().socket.getHost()));
            stores.add(new KeyValueStore("callbackPort",action.getActionInfo().getCallbackInfo().socket.getPort()));
        }
        stores.add(new KeyValueStore("isUseMessage",action.getActionInfo().isUseMessage()));
        stores.add(new KeyValueStore("isUseTemplate",action.getActionInfo().isUseTemplate()));
        stores.add(new KeyValueStore("callbackReturnStatus",action.getActionInfo().getCallbackInfo().getReturnStatus()));
        return stores;
    }


}

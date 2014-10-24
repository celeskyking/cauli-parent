package org.cauli.mock.admin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import jodd.util.Base64;
import jodd.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.cauli.common.keyvalue.KeyValueStore;
import org.cauli.mock.ServerBuilder;
import org.cauli.mock.ServerInitStatus;
import org.cauli.mock.ServerStyle;
import org.cauli.mock.admin.impl.AdminServiceImpl;
import org.cauli.mock.entity.DefaultResponse;
import org.cauli.server.HttpMethod;
import org.cauli.server.annotation.Path;
import org.cauli.server.controller.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.Map;

/**
 * @auther sky
 */

public class AdminController extends Controller {

    private Logger logger = LoggerFactory.getLogger(AdminController.class);

    AdminService service = new AdminServiceImpl();

    private JSONObject saveJSONObject(String body){
        logger.info("请求的json:{}",body);
        try{
            JSONObject jsonObject = JSON.parseObject(body);
            if(jsonObject==null){
                return null;
            }else{
                return jsonObject;
            }
        }catch (Exception e){
            return null;
        }
    }

    private void sendResponse(int errorCode,String content){
        DefaultResponse response = new DefaultResponse();
        response.setErrorCode(errorCode);
        response.setErrorInfo(content);
        renderText(JSON.toJSONString(response));
    }

    @Path(value = "/server/info",methods = HttpMethod.POST,produce = "text/json; charset=UTF-8")
    public void getServerInfo(){
        String body = body();
        JSONObject jsonObject = saveJSONObject(body);
        Map<String,Object> params = Maps.newHashMap();
        String serverName =  getServerName(jsonObject);
        if(checkJSON(jsonObject)){
            params.put("serverName",serverName);
        }else {
            return;
        }
        if(checkJSONParams(params)){
            renderJson(service.getServerInfo(serverName));
        }else{
            return;
        }
    }


    @Path(value = "/action/info",methods = HttpMethod.POST,produce = "text/json; charset=UTF-8")
    public void getActionInfo(){
        String body = body();
        JSONObject jsonObject = saveJSONObject(body);
        boolean bool = checkJSON(jsonObject);
        if(!bool){
            return;
        }
        Map<String,Object> params = Maps.newHashMap();
        String actionName = getActionName(jsonObject);
        String serverName = getServerName(jsonObject);
        params.put("actionName",actionName);
        params.put("serverName",serverName);
        boolean check = checkJSONParams(params);
        if(!check){
            return;
        }
        renderJson(service.getActionInfo(serverName,actionName));

    }

    @Path(value = "/servers",methods = HttpMethod.POST,produce = "text/json; charset=UTF-8")
    public void getServers(){
        renderJson(service.getServers());

    }

    @Path(value = "/server/actions",methods = HttpMethod.POST,produce = "text/json; charset=UTF-8")
    public void getActionsOfServer(){
        String body = body();
        JSONObject jsonObject = saveJSONObject(body);
        boolean bool = checkJSON(jsonObject);
        if(!bool){
            return;
        }
        Map<String,Object> params = Maps.newHashMap();
        String serverName =  getServerName(jsonObject);
        params.put("serverName",serverName);
        boolean check = checkJSONParams(params);
        if(!check){
            return;
        }
        renderJson(service.getActionsOfServer(serverName));
    }

    @Path(value = "/action/template",methods = HttpMethod.POST,produce = "text/plain; charset=UTF-8")
    public void getTemplateValue(){
        String body = body();
        JSONObject jsonObject = saveJSONObject(body);
        boolean bool = checkJSON(jsonObject);
        if(!bool){
            return;
        }
        String serverName = getServerName(jsonObject);
        String actionName = getActionName(jsonObject);
        String returnStatus = getReturnStatus(jsonObject);
        Map<String,Object> params = Maps.newHashMap();
        params.put("serverName",serverName);
        params.put("actionName",actionName);
        params.put("returnStatus",returnStatus);
        boolean check = checkJSONParams(params);
        if(!check){
            return;
        }
        renderText(service.getTemplateValue(serverName, actionName, returnStatus));
    }


    @Path(value = "/action/callback/template",methods = HttpMethod.POST,produce = "text/plain; charset=UTF-8")
    public void getCallbackTemplateValue(){
        String body = body();
        JSONObject jsonObject = saveJSONObject(body);
        boolean bool = checkJSON(jsonObject);
        if(!bool){
            return;
        }
        String serverName = getServerName(jsonObject);
        String actionName = getActionName(jsonObject);
        String returnStatus = getCallbackReturnStatus(jsonObject);
        Map<String,Object> params = Maps.newHashMap();
        params.put("serverName",serverName);
        params.put("actionName",actionName);
        params.put("callbackReturnStatus",returnStatus);
        boolean check = checkJSONParams(params);
        if(!check){
            return;
        }
        renderText(service.getTemplateValue(serverName, actionName, returnStatus));
    }

    @Path(value = "/action/statuses",methods = HttpMethod.POST,produce = "text/json; charset=UTF-8")
    public void getActionRetureStatus(){
        String body = body();
        JSONObject jsonObject = saveJSONObject(body);
        boolean bool = checkJSON(jsonObject);
        if(!bool){
            return;
        }
        String actionName = getActionName(jsonObject);
        String serverName = getServerName(jsonObject);
        Map<String,Object> params = Maps.newHashMap();
        params.put("actionName",actionName);
        params.put("serverName",serverName);
        boolean check = checkJSONParams(params);
        if(!check){
            return;
        }
        renderJson(service.getActionRetureStatus(serverName,actionName));
    }

    @Path(value = "/server/create",methods = HttpMethod.POST,produce = "text/json; charset=UTF-8")
    public void createServer(){
        String body = body();
        JSONObject jsonObject = saveJSONObject(body);
        boolean bool = checkJSON(jsonObject);
        if(!bool){
            return;
        }
        ServerStyle style = getServerStyle(jsonObject);
        String serverName = getServerName(jsonObject);
        int port =  getPort(jsonObject);
        ServerInitStatus initStatus= getInitStatus(jsonObject);
        Map<String,Object> params = Maps.newHashMap();
        params.put("serverName",serverName);
        params.put("serverStyle",style);
        params.put("initStatus",initStatus);
        params.put("port",port);
        boolean check = checkJSONParams(params);
        if(!check){
            return;
        }
        renderJson(service.createServer(style,serverName,port,initStatus));
    }

    @Path(value = "/action/create",methods = HttpMethod.POST,produce = "text/json; charset=UTF-8")
    public void createActionOfServer() throws Exception {
        String body = body();
        JSONObject jsonObject = saveJSONObject(body);
        boolean bool=checkJSON(jsonObject);
        if(!bool){
            return;
        }
        String actionName = getActionName(jsonObject);
        String serverName = getServerName(jsonObject);
        String path = getPath(jsonObject);
        String returnStatus = getReturnStatus(jsonObject);
        String template = Base64.decodeToString(getTemplateValue(jsonObject));
        Map<String,Object>params = Maps.newHashMap();
        params.put("actionName",actionName);
        params.put("serverName",serverName);
        params.put("path",path);
        params.put("returnStatus",returnStatus);
        params.put("templateValue",template);
        boolean check = checkJSONParams(params);
        if(!check){
            return;
        }
        if(StringUtils.isEmpty(returnStatus)){
            renderJson(service.createActionOfServer(serverName,actionName,path,template));
        }else{
            renderJson(service.createActionOfServer(serverName,actionName,path,returnStatus,template));
        }

    }

    @Path(value = "/action/template/create",methods = HttpMethod.POST,produce = "text/json; charset=UTF-8")
    public void createTemplate(){
        String body = body();
        JSONObject jsonObject = saveJSONObject(body);
        boolean bool = checkJSON(jsonObject);
        if(!bool){
            return;
        }
        String actionName = getActionName(jsonObject);
        String serverName = getServerName(jsonObject);
        String returnStatus = getReturnStatus(jsonObject);
        String templateValue = getTemplateValue(jsonObject);
        Map<String,Object> params = Maps.newHashMap();
        params.put("actionName",actionName);
        params.put("serverName",serverName);
        params.put("returnStatus",returnStatus);
        params.put("templateValue",Base64.decodeToString(templateValue));
        boolean check = checkJSONParams(params);
        if(!check){
            return;
        }
        renderJson(service.createTemplate(serverName,actionName,returnStatus,templateValue));
    }


    @Path(value = "/start/server",methods = HttpMethod.POST,produce = "text/json; charset=UTF-8")
    public void startServer(){
        String body = body();
        JSONObject jsonObject = saveJSONObject(body);
        boolean bool = checkJSON(jsonObject);
        if(!bool){
            return;
        }
        String serverName =getServerName(jsonObject);
        Map<String,Object> params = Maps.newHashMap();
        params.put("serverName",serverName);
        boolean check = checkJSONParams(params);
        if(!check){
            return;
        }
        renderJson(service.startServer(serverName));
    }

    @Path(value = "/stop/server",methods = HttpMethod.POST,produce = "text/json; charset=UTF-8")
    public void stopServer(){
        String body = body();
        JSONObject jsonObject = saveJSONObject(body);
        boolean bool = checkJSON(jsonObject);
        if(!bool){
            return;
        }
        String serverName =getServerName(jsonObject);
        Map<String,Object> params = Maps.newHashMap();
        params.put("serverName",serverName);
        boolean check = checkJSONParams(params);
        if(!check){
            return;
        }
        renderJson(service.stopServer(serverName));
    }

    @Path(value = "/restart/server",methods = HttpMethod.POST,produce = "text/json; charset=UTF-8")
    public void restartServer(){
        String body = body();
        JSONObject jsonObject = saveJSONObject(body);
        boolean bool=checkJSON(jsonObject);
        if(!bool){
            return;
        }
        String serverName =getServerName(jsonObject);
        Map<String,Object> params = Maps.newHashMap();
        params.put("serverName",serverName);
        boolean check = checkJSONParams(params);
        if(!check){
            return;
        }
        renderJson(service.restartServer(serverName));
    }

    @Path(value = "/action/update/{param}",methods = HttpMethod.POST,produce = "text/json; charset=UTF-8")
    public void updateAction(){
        String body = body();
        JSONObject jsonObject = saveJSONObject(body);
        boolean bool =checkJSON(jsonObject);
        if(!bool){
            return;
        }
        String serverName =getServerName(jsonObject);
        String actionName = getActionName(jsonObject);
        String configParam = pathParam("param");
        Map<String,Object> params = Maps.newHashMap();
        params.put("serverName",serverName);
        params.put("actionName",actionName);
        if("returnStatus".equalsIgnoreCase(configParam)){
            String returnStatus = getReturnStatus(jsonObject);
            logger.info("更新Server:{},action:{}的returnStatus为:{}",serverName,actionName,returnStatus);
            params.put("returnStatus",returnStatus);
            boolean check = checkJSONParams(params);
            if(!check){
                return;
            }
            renderJson(service.updateActionRetureStatus(serverName,actionName,returnStatus));
        }else if("templateValue".equalsIgnoreCase(configParam)){
            String returnStatus = getReturnStatus(jsonObject);
            String value = getTemplateValue(jsonObject);
            String templateValue = null;
            if(StringUtil.isNotEmpty(value)){
                templateValue = Base64.decodeToString(getTemplateValue(jsonObject));
                logger.info("更新Server:{},action:{}的templateValue为:{}",serverName,actionName,templateValue);
                params.put("returnStatus",returnStatus);
                params.put("templateValue",templateValue);
                boolean check = checkJSONParams(params);
                if(!check){
                    return;
                }
            }else{
                logger.info("更新Server:{},action:{}的templateValue为:{}",serverName,actionName,"空");
                params.put("returnStatus",returnStatus);
                params.put("templateValue","");
            }
            boolean check = checkJSONParams(params);
            if(!check){
                return;
            }
            renderJson(service.updateActionTemplateValue(serverName,actionName,returnStatus,templateValue));
        }else if("templateEncoding".equalsIgnoreCase(configParam)){
            String templateEncoding = getTemplateEncoding(jsonObject) ;
            logger.info("更新Server:{},action:{}的templateEncoding为:{}",serverName,actionName,templateEncoding);
            params.put("templateEncoding",templateEncoding);
            boolean check = checkJSONParams(params);
            if(!check){
                return;
            }
            renderJson(service.updateActionTemplateEncoding(serverName,actionName,templateEncoding));
        }else if("delay".equalsIgnoreCase(configParam)){
            long delay = getTimeoutMS(jsonObject) ;
            logger.info("更新Server:{},action:{}的dalay为:{}",serverName,actionName,delay);
            params.put("templateValue",delay);
            boolean check = checkJSONParams(params);
            if(!check){
                return;
            }
            renderJson(service.updateActionTimeOutMS(serverName,actionName,delay));
        }else if("isUseTemplate".equalsIgnoreCase(configParam)){
            boolean isUseTemplate = getIsUseTemplate(jsonObject);
            logger.info("更新Server:{},action:{}的isUseTemplate为:{}",serverName,actionName,isUseTemplate);
            params.put("isUseTemplate",isUseTemplate);
            boolean check = checkJSONParams(params);
            if(!check){
                return;
            }
            renderJson(service.updateActionIsUseTemplate(serverName,actionName,isUseTemplate));
        }else if("isUseCallbackTemplate".equalsIgnoreCase(configParam)){
            boolean isUseCallbackTemplate = getIsUseCallbackTemplate(jsonObject);
            logger.info("更新Server:{},action:{}的isUseCallbackTemplate为:{}",serverName,actionName,isUseCallbackTemplate);
            params.put("isCallbackUseTemplate",isUseCallbackTemplate);
            boolean check = checkJSONParams(params);
            if(!check){
                return;
            }
            renderJson(service.updateActionIsUseCallbackTemplate(serverName, actionName, isUseCallbackTemplate));
        }else if("isUseMessage".equalsIgnoreCase(configParam)){
            boolean isUseMessage = getIsUseMessage(jsonObject);
            logger.info("更新Server:{},action:{}的isUseMessage为:{}",serverName,actionName,isUseMessage);
            params.put("isUseMessage",isUseMessage);
            boolean check = checkJSONParams(params);
            if(!check){
                return;
            }
            renderJson(service.updateActionIsUseMessage(serverName,actionName,isUseMessage));
        }else if("callbackURL".equalsIgnoreCase(configParam)){
            String url = getCallbackUrl(jsonObject);
            logger.info("更新Server:{},action:{}的callbackURL为:{}",serverName,actionName,url);
            params.put("callbackURL",url);
            boolean check = checkJSONParams(params);
            if(!check){
                return;
            }
            renderJson(service.updateActionCallBackUrl(serverName,actionName,url));
        }else if("callbackReturnStatus".equalsIgnoreCase(configParam)){
            String status = getCallbackReturnStatus(jsonObject);
            logger.info("更新Server:{},action:{}的callbackReturnStatus为:{}",serverName,actionName,status);
            params.put("callbackReturnStatus",status);
            boolean check = checkJSONParams(params);
            if(!check){
                return;
            }
            renderJson(service.updateActionCallBackReturnStatus(serverName,actionName,status));
        }else if("callbackTemplate".equalsIgnoreCase(configParam)){
            String callbackTemplate = getCallbackTemplate(jsonObject);
            if(StringUtil.isNotEmpty(callbackTemplate)){
                callbackTemplate=Base64.decodeToString(callbackTemplate);
            }
            String callbackReturnStatus = getCallbackReturnStatus(jsonObject);
            logger.info("更新Server:{},action:{}的callbackTemplate为:{}",serverName,actionName,callbackTemplate);
            params.put("callbackTemplate",callbackTemplate);
            params.put("callbackReturnStatus",callbackReturnStatus);
            boolean check =checkJSONParams(params);
            if(!check){
                return;
            }
            renderJson(service.updateActionCallBackTemplate(serverName,actionName,callbackReturnStatus,callbackTemplate));
        }else{
            logger.error("未查询到该属性值:{},更新失败",configParam);
            DefaultResponse response = new DefaultResponse();
            response.setErrorInfo("不能够查找到该属性"+configParam);
            response.setErrorCode(9);
            renderJson(response);
        }

    }

    @Path(value = "/action/callback",methods = HttpMethod.POST,produce = "text/json; charset=UTF-8")
    public void docallback(){
        String body = body();
        JSONObject jsonObject = saveJSONObject(body);
        boolean bool = checkJSON(jsonObject);
        if(!bool){
            return;
        }
        String actionName =getActionName(jsonObject);
        String serverName = getServerName(jsonObject);
        String callbackName = getCallbackName(jsonObject);
        Map<String,Object> params = Maps.newHashMap();
        params.put("actionName",actionName);
        params.put("serverName",serverName);
        params.put("callbackName",callbackName);
        boolean check = checkJSONParams(params);
        if(!check){
            return;
        }
        renderJson(service.doCallback(serverName,actionName,callbackName));
    }

    @Path(value = "/action/callback/template",methods = HttpMethod.POST,produce = "text/json; charset=UTF-8")
    public void callbackTemplate(){
        String body = body();
        JSONObject jsonObject = saveJSONObject(body);
        boolean bool = checkJSON(jsonObject);
        if(!bool){
            return;
        }
        String actionName =getActionName(jsonObject);
        String serverName = getServerName(jsonObject);
        String callbackReturnStatus = getCallbackReturnStatus(jsonObject);
        Map<String,Object> params = Maps.newHashMap();
        params.put("actionName",actionName);
        params.put("serverName",serverName);
        params.put("callbackReturnStatus",callbackReturnStatus);
        boolean check = checkJSONParams(params);
        if(!check){
            return;
        }
        renderText(service.getCallbackTemplateValue(serverName,actionName,callbackReturnStatus));
    }

    @Path(value = "/action/callbacks",methods = HttpMethod.POST,produce = "text/json; charset=UTF-8")
    public void getCallbacks(){
        String body = body();
        JSONObject jsonObject = saveJSONObject(body);
        boolean bool = checkJSON(jsonObject);
        if(!bool){
            return;
        }
        String actionName =getActionName(jsonObject);
        String serverName = getServerName(jsonObject);
        Map<String,Object> params = Maps.newHashMap();
        params.put("actionName",actionName);
        params.put("serverName",serverName);
        boolean check = checkJSONParams(params);
        if(!check){
            return;
        }
        renderJson(service.getCallbacksofAction(serverName,actionName));
    }

    @Path(value = "/action/callback/statuses",methods = HttpMethod.POST,produce = "text/json; charset=UTF-8")
    public void getCallbackReturnStatuses(){
        String body = body();
        JSONObject jsonObject = saveJSONObject(body);
        boolean bool = checkJSON(jsonObject);
        if(!bool){
            return;
        }
        String actionName =getActionName(jsonObject);
        String serverName = getServerName(jsonObject);
        Map<String,Object> params = Maps.newHashMap();
        params.put("actionName",actionName);
        params.put("serverName",serverName);
        boolean check = checkJSONParams(params);
        if(!check){
            return;
        }
        renderJson(service.getCallbackReturnStatuses(serverName,actionName));
    }

    @Path(value = "/action/request",methods = HttpMethod.POST,produce = "text/json; charset=UTF-8")
    public void getRequestsOfAction(){
        String body = body();
        JSONObject jsonObject = saveJSONObject(body);
        boolean bool = checkJSON(jsonObject);
        if(!bool){
            return;
        }
        String actionName =getActionName(jsonObject);
        String serverName = getServerName(jsonObject);
        String date = getDate(jsonObject);
        Map<String,Object> params = Maps.newHashMap();
        params.put("actionName",actionName);
        params.put("serverName",serverName);
        params.put("date",date);
        boolean check = checkJSONParams(params);
        if(!check){
            return;
        }
        renderJson(service.getRequestHistory(serverName,actionName,date));
    }

    @Path(value = "/action/requestKeys",methods = HttpMethod.POST,produce = "text/json; charset=UTF-8")
    public void getRequestKeysOfAction(){
        String body = body();
        JSONObject jsonObject = saveJSONObject(body);
        boolean bool = checkJSON(jsonObject);
        if(!bool){
            return;
        }
        String actionName =getActionName(jsonObject);
        String serverName = getServerName(jsonObject);
        Map<String,Object> params = Maps.newHashMap();
        params.put("actionName",actionName);
        params.put("serverName",serverName);
        boolean check = checkJSONParams(params);
        if(!check){
            return;
        }
        renderJson(service.getRequestHistoryKeys(serverName,actionName));
    }

    //--------------private methods---------------

    private String getServerName(JSONObject jsonObject){
        return jsonObject.getString("serverName");
    }

    private String getDate(JSONObject jsonObject){
        return jsonObject.getString("date");
    }

    private String getActionName(JSONObject jsonObject){
        return jsonObject.getString("actionName");
    }

    private String getReturnStatus(JSONObject jsonObject){
        return jsonObject.getString("returnStatus");
    }

    private String getTemplateValue(JSONObject jsonObject){
        return jsonObject.getString("templateValue");
    }

    private ServerStyle getServerStyle(JSONObject jsonObject){
        try{
            return ServerStyle.valueOf(jsonObject.getString("serverStyle"));
        } catch (Exception e){
            return null;
        }

    }

    private int getPort(JSONObject jsonObject){
        return jsonObject.getInteger("port");
    }

    private ServerInitStatus getInitStatus(JSONObject jsonObject){
        try{
            return ServerInitStatus.valueOf(jsonObject.getString("initStatus").toUpperCase());
        }catch (Exception e){
            return null;
        }

    }

    private String getPath(JSONObject object){
        return object.getString("path");
    }

    private String getTemplateEncoding(JSONObject object){
        return object.getString("templateEncoding");
    }

    private long getTimeoutMS(JSONObject jsonObject){
        return jsonObject.getLong("delay");
    }


    private boolean getIsUseTemplate(JSONObject jsonObject){
        return jsonObject.getBoolean("isUseTemplate");
    }

    private boolean getIsUseCallbackTemplate(JSONObject jsonObject){
        return jsonObject.getBoolean("isUseCallbackTemplate");
    }

    private boolean getIsUseMessage(JSONObject jsonObject){
        return jsonObject.getBoolean("isUseMessage");
    }

    private String getCallbackUrl(JSONObject jsonObject){
        return jsonObject.getString("callbackURL");
    }

    private String getCallbackName(JSONObject jsonObject){
        return jsonObject.getString("callbackName");
    }

    private String getCallbackReturnStatus(JSONObject jsonObject){
        return jsonObject.getString("callbackReturnStatus");
    }

    private String getCallbackTemplate(JSONObject jsonObject){
        return jsonObject.getString("callbackTemplate");
    }


    private boolean checkJSONParams(Map<String,Object> params){
        for(Map.Entry<String,Object> entry :params.entrySet()){
            if(entry.getValue() instanceof String){
                if(StringUtils.isEmpty((String) entry.getValue())){
                    logger.error("获取数据失败,请求的JSON串未获取到{}的值",entry.getKey());
                    sendResponse(2, "获取数据失败,请求的JSON串未获取到"+entry.getKey()+"的值,有可能值错误.");
                    return false;
                }
            }else{
                if(entry.getValue()==null){
                    logger.error("获取数据失败,请求的JSON串未获取到{}的值",entry.getKey());
                    sendResponse(2, "获取数据失败,请求的JSON串未获取到"+entry.getKey()+"的值,有可能值错误.");
                    return false;
                }
            }

        }
        return true;
    }

    private boolean checkJSON(JSONObject jsonObject){
        if(jsonObject==null){
            logger.error("请求的JSON串格式错误");
            sendResponse(1, "请求的JSON串格式错误");
            return false;
        }
        return true;
    }

}

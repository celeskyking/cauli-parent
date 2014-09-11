package org.cauli.mock.admin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import jodd.util.Base64;
import org.apache.commons.lang3.StringUtils;
import org.cauli.mock.ServerBuilder;
import org.cauli.mock.ServerInitStatus;
import org.cauli.mock.ServerStyle;
import org.cauli.mock.admin.impl.AdminServiceImpl;
import org.cauli.mock.entity.DefaultResponse;
import org.cauli.server.annotation.Path;
import org.cauli.server.controller.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;

/**
 * @auther sky
 */

public class AdminController extends Controller {

    private Logger logger = LoggerFactory.getLogger(AdminController.class);

    AdminService service = new AdminServiceImpl();

    private JSONObject saveJSONObject(String body){
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

    @Path("/server/info")
    public void getServerInfo(){
        String body = body();
        JSONObject jsonObject = saveJSONObject(body);
        if(jsonObject==null){
            logger.error("请求的JSON串格式错误");
            sendResponse(1, "请求的JSON串格式错误");
            return;
        }
        String serverName =  jsonObject.getString("serverName");
        if(StringUtils.isEmpty(serverName)){
            logger.error("获取ServerName失败,请求的JSON串未获取到serverName值");
            sendResponse(2, "获取ServerName失败,请求的JSON串未获取到serverName值");
            return;
        }
        renderJson(service.getServerInfo(serverName));
    }


    @Path("/action/info")
    public void getActionInfo(){
        String body = body();
        JSONObject jsonObject = saveJSONObject(body);
        if(jsonObject==null){
            logger.error("请求的JSON串格式错误");
            sendResponse(1,"请求的JSON串格式错误");
            return;
        }
        String actionName =  jsonObject.getString("actionName");
        String serverName =  jsonObject.getString("serverName");
        if(StringUtils.isEmpty(actionName)|| StringUtils.isEmpty(serverName)){
            logger.error("获取数据失败,请求的JSON串未获取到serverName值或者actionName值");
            sendResponse(2, "获取数据失败,请求的JSON串未获取到serverName值或者actionName值");
            return;
        }
        renderJson(service.getActionInfo(serverName,actionName));

    }

    @Path("/servers")
    public void getServers(){
        renderJson(service.getServers());

    }

    @Path("/server/actions")
    public void getActionsOfServer(){
        String body = body();
        JSONObject jsonObject = saveJSONObject(body);
        if(jsonObject==null){
            logger.error("请求的JSON串格式错误");
            sendResponse(1,"请求的JSON串格式错误");
            return;
        }
        String serverName =  jsonObject.getString("serverName");
        if(StringUtils.isEmpty(serverName)){
            logger.error("获取ServerName失败,请求的JSON串未获取到serverName值");
            sendResponse(2, "获取ServerName失败,请求的JSON串未获取到serverName值");
            return;
        }
        renderJson(service.getActionsOfServer(serverName));
    }

    @Path("/action/template")
    public void getTemplateValue(){
        String body = body();
        JSONObject jsonObject = saveJSONObject(body);
        if(jsonObject==null){
            logger.error("请求的JSON串格式错误");
            sendResponse(1,"请求的JSON串格式错误");
            return;
        }
        String serverName = jsonObject.getString("serverName");
        String actionName = jsonObject.getString("actionName");
        String returnStatus = jsonObject.getString("returnStatus");
        if(StringUtils.isEmpty(serverName)||StringUtils.isEmpty(actionName)||StringUtils.isEmpty(returnStatus)){
            logger.error("获取数据失败,未获取到serverName,actionName或者returnStatus");
            sendResponse(2, "获取数据失败,未获取到serverName,actionName或者returnStatus");
        }
        renderText(service.getTemplateValue(serverName, actionName, returnStatus));
    }

    @Path("/action/statuses")
    public void getActionRetureStatus(){
        String body = body();
        JSONObject jsonObject = saveJSONObject(body);
        if(jsonObject==null){
            logger.error("请求的JSON串格式错误");
            sendResponse(1,"请求的JSON串格式错误");
            return;
        }
        String actionName = jsonObject.getString("actionName");
        String serverName = jsonObject.getString("serverName");
        if(StringUtils.isEmpty(actionName)|| StringUtils.isEmpty(serverName)){
            logger.error("获取数据失败,请求的JSON串未获取到serverName值或者actionName值");
            sendResponse(2, "获取数据失败,请求的JSON串未获取到serverName值或者actionName值");
            return;
        }
        renderJson(service.getActionRetureStatus(serverName,actionName));
    }

    @Path("/server/create")
    public void createServer(){
        String body = body();
        JSONObject jsonObject = saveJSONObject(body);
        if(jsonObject==null){
            logger.error("请求的JSON串格式错误");
            sendResponse(1,"请求的JSON串格式错误");
            return;
        }
        ServerStyle style = ServerStyle.valueOf( jsonObject.getString("serverStyle"));
        String serverName = jsonObject.getString("serverName");
        int port =  jsonObject.getInteger("port");
        ServerInitStatus initStatus= ServerInitStatus.valueOf(jsonObject.getString("initStatus"));
        if(StringUtils.isEmpty(serverName)){
            logger.error("获取数据失败,请求的JSON串未获取到serverName值或者port值或者serverName或者initStatus");
            sendResponse(2, "获取数据失败,请求的JSON串未获取到serverName值或者port值或者serverName或者initStatus");
            return;
        }
        if(ServerBuilder.getInstance().checkPortisUsed(port)){
            logger.error("端口被占用,Server创建失败,Port:"+port+", ServerName:"+serverName);
            sendResponse(5, "服务器端口被占用，不能够创建Server");
        }
        renderJson(service.createServer(style,serverName,port,initStatus));
    }

    @Path("/action/create")
    public void createActionOfServer() throws Exception {
        String body = body();
        JSONObject jsonObject = saveJSONObject(body);
        if(jsonObject==null){
            logger.error("请求的JSON串格式错误");
            sendResponse(1,"请求的JSON串格式错误");
            return;
        }
        String actionName = jsonObject.getString("actionName");
        String serverName = jsonObject.getString("serverName");
        String path = jsonObject.getString("path");
        String returnStatus = jsonObject.getString("returnStatus");
        String template = Base64.decodeToString(jsonObject.getString("templateValue"));
        if(StringUtils.isEmpty(serverName)||StringUtils.isEmpty(actionName)||StringUtils.isEmpty(path)||
                StringUtils.isEmpty(template)){
            logger.error("获取数据失败,请求的JSON串未获取到serverName值或者actionName值或者path值或者templateValue值");
            sendResponse(2, "获取数据失败,请求的JSON串未获取到serverName值或者actionName值或者path值或者templateValue值");
            return;
        }
        if(StringUtils.isEmpty(returnStatus)){
            renderJson(service.createActionOfServer(serverName,actionName,path,template));
        }else{
            renderJson(service.createActionOfServer(serverName,actionName,path,returnStatus,template));
        }

    }

    @Path("/action/template/create")
    public void createTemplate(){
        String body = body();
        JSONObject jsonObject = saveJSONObject(body);
        if(jsonObject==null){
            logger.error("请求的JSON串格式错误");
            sendResponse(1,"请求的JSON串格式错误");
            return;
        }
        String actionName = jsonObject.getString("actionName");
        String serverName = jsonObject.getString("serverName");
        String returnStatus = jsonObject.getString("returnStatus");
        String templateValue = jsonObject.getString("templateValue");
        if(StringUtils.isEmpty(serverName)||StringUtils.isEmpty(actionName)||StringUtils.isEmpty(returnStatus)||StringUtils.isEmpty(templateValue)){
            logger.error("获取数据失败,请求的JSON串未获取到serverName值或者actionName值或者returnStatus值");
            sendResponse(2, "获取数据失败,请求的JSON串未获取到serverName值或者actionName值或者returnStatus值");
            return;
        }
        renderJson(service.createTemplate(serverName,actionName,returnStatus,templateValue));
    }

    @Override
    public void before() {
        charset(Charset.forName("UTF-8"));
    }

    @Override
    public void after() {

    }
}

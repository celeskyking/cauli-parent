package org.cauli.mock.admin;

import org.cauli.mock.ServerInitStatus;
import org.cauli.mock.ServerStyle;

import java.util.Map;

/**
 * @auther sky
 */
public interface AdminService {


    public String getServerInfo(String serverName);

    public String getActionInfo(String serverName,String actionName);

    public String getServers();

    public String getActionsOfServer(String serverName);

    public String getTemplateValue(String serverName,String actionName,String returnStatus);

    public String getActionRetureStatus(String serverName,String actionName);

    public String createServer(ServerStyle serverStyle,String serverName,int port,ServerInitStatus initStatus);

    public String createActionOfServer(String serverName,String actionName,String path,String temlateValue) throws Exception;

    public String createActionOfServer(String serverName,String actionName,String path,String status,String temlateValue) throws Exception;

    public String createTemplate(String ServerName,String actionName,String returnStatus,String templateValue);

    public String startServer(String serverName);

    public String stopServer(String serverName);

    public String restartServer(String serverName);

    public String updateActionRetureStatus(String serverName,String actionName,String returnStatus);

    public String updateActionTemplateValue(String serverName,String actionName,String returnStatus,String templateValue);

    public String updateActionTimeOutMS(String serverName,String actionName,long timeoutMS);

    public String updateActionIsUseTemplate(String serverName,String actionName,boolean isUseTemplate);

    public String updateActionIsUseMessage(String serverName,String actionName,boolean isUseMessage);

    public String updateActionTemplateEncoding(String serverName,String actionName,String templateEncoding);

    public String doCallback(String serverName,String actionName,String callbackName);

    public String updateActionCallBackUrl(String serverName,String action,String callbackUrl);

    public String updateActionCallBackReturnStatus(String serverName,String action,String callbackReturnStatus);

    public String updateActionCallBackTemplate(String serverName,String action,String returnStatus ,String callbackTemplate);
}

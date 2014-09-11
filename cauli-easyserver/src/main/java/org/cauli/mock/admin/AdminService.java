package org.cauli.mock.admin;

import org.cauli.mock.ServerInitStatus;
import org.cauli.mock.ServerStyle;

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

}

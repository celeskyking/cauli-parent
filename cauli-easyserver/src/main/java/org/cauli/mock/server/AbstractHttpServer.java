package org.cauli.mock.server;


import com.google.common.collect.Maps;
import jodd.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.cauli.common.instrument.ClassUtils;
import org.cauli.mock.MockHandler;
import org.cauli.mock.ServerInitStatus;
import org.cauli.mock.ServerProtocol;
import org.cauli.mock.ServerStatus;
import org.cauli.mock.action.AbstractHttpAction;
import org.cauli.mock.action.ActionExcuter;
import org.cauli.mock.action.DefaultHttpAction;
import org.cauli.mock.action.MockAction;
import org.cauli.mock.annotation.Route;
import org.cauli.mock.annotation.ServerConfig;
import org.cauli.mock.constant.Constant;
import org.cauli.mock.entity.ParametersModel;
import org.cauli.mock.entity.ServerInfo;
import org.cauli.mock.exception.ServerNameNotSupportChineseException;
import org.cauli.mock.util.CommonUtil;
import org.cauli.server.HttpRequest;
import org.cauli.server.WebServer;
import org.cauli.server.WebServers;
import org.cauli.server.annotation.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;

/**
 * Created by tianqing.wang on 2014/8/14
 */
public abstract class AbstractHttpServer implements MockServer<AbstractHttpAction>{

    private Logger logger = LoggerFactory.getLogger(AbstractHttpServer.class);
    private Map<String,AbstractHttpAction> mockActionUriMapping = Maps.newHashMap();
    private Map<String,AbstractHttpAction> actionMap = Maps.newHashMap();
    private WebServer webServer;
    private MockHandler mockHandle=new MockHandler();
    private boolean isRouteExist=false;
    private Method routeMethod;
    public ServerInfo serverInfo=new ServerInfo();
    private String requestUri;


    public AbstractHttpServer() {
        serverInfo.setStatus(ServerStatus.STOP);
        serverInfo.setProtocol(ServerProtocol.HTTP);
        if(getClass().isAnnotationPresent(ServerConfig.class)){
            ServerConfig serverConfig=getClass().getAnnotation(ServerConfig.class);
            serverInfo.setServerName(serverConfig.value());
            serverInfo.setPort(serverConfig.port());
        }
        if(getClass().isAnnotationPresent(Path.class)){
            Path uri = getClass().getAnnotation(Path.class);
            setRequestUri(uri.value());
        }
        this.config(serverInfo);
        this.mockHandle.setServer(this);
        if(StringUtil.isEmpty(serverInfo.getServerName())){
            serverInfo.setServerName(getClass().getSimpleName());
        }
        if(CommonUtil.checkContainsChinese(serverInfo.getServerName())){
            throw new ServerNameNotSupportChineseException("ServerName不支持非英文:"+serverInfo.getServerName());
        }
    }

    @Override
    public AbstractHttpAction createAction(String actionName,String path,String template) {
        return createAction(actionName,path, Constant.DEFAULT_RETURN_STATUS,template);
    }

    @Override
    public AbstractHttpAction createAction(String actionName, String path, String returnStatus, String templateValue) {
        DefaultHttpAction action = new DefaultHttpAction();
        action.getActionInfo().setActionName(actionName);
        action.setServer(this);
        action.setRequestUri(path);
        action.addTemplate(returnStatus,templateValue);
        this.actionMap.put(action.getActionName(),action);
        this.mockActionUriMapping.put(path,action);
        return action;
    }

    public void loadActions(){
        parseActions();
    }

    public ServerInfo getServerInfo() {
        return serverInfo;
    }

    public void setBankInfo(ServerInfo serverInfo) {
        this.serverInfo = serverInfo;
    }

    public abstract void config(ServerInfo serverInfo);

    public String getRequestUri() {
        return requestUri;
    }

    public void setRequestUri(String requestUri) {
        this.requestUri = requestUri;
    }






    public boolean isSingleUrl(){
        if(StringUtil.isEmpty(getRequestUri())){
            return false;
        }else{
            return true;
        }
    }

    public MockAction getAction(HttpRequest request,ParametersModel pairs) throws Exception {
        if(isSingleUrl()){
            if(!checkRouteExist()){
                throw new Exception("如果配置HttpServer的RequestMapping,则必须配置转化路由方法,@Route标注的方法,返回值必须为MockAction类型或者子类");
            }else{
                ActionExcuter actionExcuter = new ActionExcuter(routeMethod,pairs,this);
                AbstractHttpAction action = (AbstractHttpAction) actionExcuter.invoke();
                if(action!=null){
                    action.setParametersModel(pairs);
                    if(action.getActionInfo().isUseTemplate()){
                        action.loadTemplate();
                    }
                    return action;
                }else{
                    return null;
                }
            }
        }else{
            String uri = StringUtils.substringBefore(request.uri(),"?");
            AbstractHttpAction action= this.mockActionUriMapping.get(uri);
            if(action!=null){
                action.setParametersModel(pairs);
                return action;
            }else{
                return null;
            }
        }
    }

    public Map<String,AbstractHttpAction> getHttpMockActionsPairs(){
        return this.actionMap;
    }


    public java.util.Collection<AbstractHttpAction> getHttpMockActions(){
        return this.actionMap.values();
    }



    public int port(){
        return serverInfo.getPort();
    }

    @Override
    public void start() throws Exception {
        if(getServerStatus()!=ServerStatus.START){

            if(getPort()==-1){
                throw new RuntimeException(this.serverInfo.getServerName()+"端口号未设置");
            }
            webServer = WebServers.createWebServer(getPort())
                    .add(mockHandle);
            if(isHttps()){
                webServer.setupSsl(new FileInputStream(new File(serverInfo.getKeyStore())),
                        serverInfo.getKeyStorePassword(),serverInfo.getKeyPassword());
            }
            webServer.start().get();
            logger.info("启动HttpServer[{}],端口为:{}",serverInfo.getServerName(),serverInfo.getPort());
            this.serverInfo.setStatus(ServerStatus.START);
        }

    }

    @Override
    public void stop() {

        if(webServer!=null&&getServerStatus()!=ServerStatus.STOP){
            webServer.stop();
            this.serverInfo.setStatus(ServerStatus.STOP);
            logger.info("关闭Server:{}",getServerName());
        }

    }

    @Override
    public void restart() throws Exception {
        stop();
        webServer=null;
        start();
        logger.info("[{}] 重新启动完成...",this.serverInfo.getServerName());
    }

    @Override
    public int getPort() {
        return serverInfo.getPort();
    }

    @Override
    public String getServerName() {
        return serverInfo.getServerName();
    }

    @Override
    public ServerStatus getServerStatus() {
        return serverInfo.getStatus();
    }

    @Override
    public ServerProtocol getProtocol() {
        return serverInfo.getProtocol();
    }


    @Override
    public java.util.Collection<AbstractHttpAction> getActions() {
        return getHttpMockActions();
    }

    @Override
    public AbstractHttpAction getAction(String actionName) {
        AbstractHttpAction action= this.actionMap.get(actionName);
        return action;
    }


    @Override
    public ServerInitStatus getInitStatus() {
        return serverInfo.getInitStatus();
    }



    //------------------------------------private help method-------------------------------------------

    private boolean isHttps(){
        if(StringUtil.isEmpty(serverInfo.getKeyStore())){
            return false;
        }else{
            return true;
        }
    }


    private void parseActions() {
            Field[] fields = getClass().getDeclaredFields();
            try{
                for(Field field:fields){
                    field.setAccessible(true);
                    if(!Modifier.isStatic(field.getModifiers())&& ClassUtils.isAssignableFromSubClass(AbstractHttpAction.class, field.getType())){
                        AbstractHttpAction action = (AbstractHttpAction) field.get(this);
                        if(field.isAnnotationPresent(Path.class)){
                            Path path = field.getAnnotation(Path.class);
                            if(StringUtils.isNotEmpty(path.value())){
                                action.setRequestUri(path.value());
                            }
                        }
                        action.setServer(this);
                        if(action.getActionInfo().isUseTemplate()){
                            action.loadTemplate();
                        }
                        if(StringUtil.isEmpty(action.getActionName())){
                            action.getActionInfo().setActionName(field.getName());
                        }
                        actionMap.put(action.getActionName(),action);
                        if(StringUtil.isNotEmpty(action.getRequestUri())){
                            this.mockActionUriMapping.put(action.getRequestUri(),action);
                        }
                    }
                }
                Method[] methods = getClass().getDeclaredMethods();
                for(Method method:methods){
                    if(method.isAnnotationPresent(Route.class)&&ClassUtils.isAssignableFromSubClass(MockAction.class,method.getReturnType())){
                        this.isRouteExist=true;
                        this.routeMethod = method;
                        break;
                    }
                }
            }catch (Exception e){
                throw new RuntimeException(e);
            }
    }

    private boolean checkRouteExist(){
        return isRouteExist;
    }









}

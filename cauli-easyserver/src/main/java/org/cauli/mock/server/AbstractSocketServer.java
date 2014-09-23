package org.cauli.mock.server;

import com.google.common.collect.Maps;
import jodd.util.StringUtil;
import org.cauli.common.instrument.ClassUtils;
import org.cauli.mock.ServerInitStatus;
import org.cauli.mock.ServerProtocol;
import org.cauli.mock.ServerStatus;
import org.cauli.mock.action.AbstractSocketAction;
import org.cauli.mock.action.DefaultSocketAction;
import org.cauli.mock.annotation.ServerConfig;
import org.cauli.mock.constant.Constant;
import org.cauli.mock.context.Context;
import org.cauli.mock.entity.KeyValueStore;
import org.cauli.mock.entity.KeyValueStores;
import org.cauli.mock.entity.ServerInfo;
import org.cauli.mock.exception.ServerNameNotSupportChineseException;
import org.cauli.mock.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by tianqing.wang on 2014/8/29
 */
public abstract class AbstractSocketServer implements MockServer<AbstractSocketAction>{

    private Logger logger = LoggerFactory.getLogger(AbstractSocketServer.class);

    private ServerInfo serverInfo=new ServerInfo();

    private ISocketServer socketServer;

    private Map<String,AbstractSocketAction> actionMap = Maps.newHashMap();

    private Context context=new Context();



    public String getServerName(){
        return serverInfo.getServerName();
    }

    @Override
    public AbstractSocketAction createAction(String actionName,String path,String templateValue) {
        return createAction(actionName,path,"SUCCESS",templateValue);
    }


    @Override
    public AbstractSocketAction createAction(String actionName, String path, String returnStatus, String templateValue) {
        DefaultSocketAction action = new DefaultSocketAction();
        action.getActionInfo().setActionName(actionName);
        action.addTemplate(returnStatus,templateValue);
        this.actionMap.put(action.getActionName(),action);
        return action;
    }

    public AbstractSocketServer(){
        serverInfo.setStatus(ServerStatus.STOP);
        serverInfo.setAsyn(Constant.DEFAULT_SOCKET_AYNC);
        serverInfo.setProtocol(ServerProtocol.SOCKET);
        if(getClass().isAnnotationPresent(ServerConfig.class)){
            ServerConfig serverConfig=getClass().getAnnotation(ServerConfig.class);
            serverInfo.setServerName(serverConfig.value());
            serverInfo.setPort(serverConfig.port());
        }
        config(serverInfo);
        if(StringUtil.isEmpty(serverInfo.getServerName())){
            serverInfo.setServerName(getClass().getSimpleName());
        }
        if(CommonUtil.checkContainsChinese(serverInfo.getServerName())){
            throw new ServerNameNotSupportChineseException("ServerName不支持非英文:"+serverInfo.getServerName());
        }
    }

    public ServerInfo getServerInfo() {
        return serverInfo;
    }

    public abstract AbstractSocketAction route(String request);

    public abstract void config(ServerInfo serverInfo);

    public void loadActions() {

        parseActions();
    }

    @Override
    public void start() throws Exception {
        this.socketServer.setRequestEncoding(serverInfo.getRequestEncoding());
        this.socketServer.setResponseEncoding(serverInfo.getResponseEncoding());
        if(serverInfo.isAsyn()){
            this.socketServer=new SocketNIOServer(this);
            this.socketServer.start();
        }else{
            this.socketServer=new MultiThreadServer(this);
            this.socketServer.start();
        }
        serverInfo.setStatus(ServerStatus.START);
    }

    @Override
    public void stop() {
        this.socketServer.stop();
        serverInfo.setStatus(ServerStatus.STOP);
    }

    @Override
    public void restart() throws Exception {
        start();
        stop();
    }

    @Override
    public int getPort() {
        return serverInfo.getPort();
    }


    @Override
    public ServerStatus getServerStatus() {
        return serverInfo.getStatus();
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public ServerProtocol getProtocol() {
        return ServerProtocol.SOCKET;
    }

    @Override
    public Collection getActions() {
        return actionMap.values();
    }

    @Override
    public AbstractSocketAction getAction(String actionName) {
        return actionMap.get(actionName);
    }

    @Override
    public ServerInitStatus getInitStatus() {
        return serverInfo.getInitStatus();
    }

    public void parseActions() {
        Field[] fields = getClass().getDeclaredFields();
        try{
            for(Field field:fields){
                field.setAccessible(true);
                if(!Modifier.isStatic(field.getModifiers())
                        && ClassUtils.isAssignableFromSubClass(AbstractSocketAction.class, field.getType())){
                    AbstractSocketAction action = (AbstractSocketAction) field.get(this);
                    logger.info("Server:{} 添加:Action: {}", serverInfo.getServerName(), action.getActionName());
                    if(StringUtil.isEmpty(action.getActionName())){
                        action.getActionInfo().setActionName(field.getName());
                        action.load();
                    }
                    action.setServer(this);
                    actionMap.put(action.getActionName(),action);
                }
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }


    }


    public AbstractSocketAction getDefaultAction(){
        Iterator<String> iterator = this.actionMap.keySet().iterator();
        while(iterator.hasNext()){
            String actionName = iterator.next();
            return this.actionMap.get(actionName);
        }
        return null;
    }

    @Override
    public void addContext(KeyValueStore store) {
        context.addContext(store.getKey(),store.getValue());
    }

    @Override
    public void addContext(KeyValueStores stores) {
        context.addContext(stores.toMap());
    }


}

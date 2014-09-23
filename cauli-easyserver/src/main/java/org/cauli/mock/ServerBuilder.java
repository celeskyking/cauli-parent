package org.cauli.mock;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import jodd.bean.BeanCopy;
import org.cauli.common.instrument.ClassPool;
import org.cauli.common.instrument.ClassUtils;
import org.cauli.mock.context.Context;
import org.cauli.mock.context.ContextFactory;
import org.cauli.mock.entity.ServerInfo;
import org.cauli.mock.server.AbstractHttpServer;
import org.cauli.mock.server.MockServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.Set;


/**
 * @auther sky
 */
public class ServerBuilder {

    private Logger logger = LoggerFactory.getLogger(ServerBuilder.class);

    private Context context = ContextFactory.getContext();

    private volatile static ServerBuilder serverBuilder;

    private Map<String,MockServer> servers= Maps.newHashMap();

    private Set<Integer> isUsingPorts = Sets.newHashSet();

    private ServerBuilder(){
        try {
            initServers();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("加载MockServer的时候出现了异常",e);
        }
    }

    public static ServerBuilder getInstance(){
        if(serverBuilder==null){
            synchronized (ServerBuilder.class){
                if(serverBuilder==null){
                    serverBuilder=new ServerBuilder();
                }
            }
        }
        return serverBuilder;
    }

    protected void initServers() throws Exception {
        if(servers.size()==0){
            try{
                Set<Class<?>> classes = ClassPool.getClassPool();
                for(Class<?> clazz:classes){
                    if(ClassUtils.isAssignableFromSubClass(MockServer.class, clazz)){
                        logger.info("解析MockServer:{}",clazz.getName());
                        if(!clazz.isInterface()&& !Modifier.isAbstract(clazz.getModifiers())&&!clazz.getName().contains("$")){
                            MockServer server = (MockServer)clazz.newInstance();
                            server.getContext().setParent(context);
                            isUsingPorts.add(server.getPort());
                            server.loadActions();
                            servers.put(server.getServerName(),server);
                            if(server.getInitStatus()==ServerInitStatus.START){
                                logger.info("启动Server:{}",server.getServerName());
                                server.start();
                            }
                        }else{
                            logger.info("忽略MockServer class：{}",clazz.getName());
                        }

                    }
                }
            }catch (Exception e){
                e.printStackTrace();
                throw new Exception(e);
            }
        }
    }


    public Map<String, MockServer> getServers() {
        return servers;
    }

    public void setServers(Map<String, MockServer> servers) {
        this.servers = servers;
    }

    public AbstractHttpServer createHttpServer(final ServerInfo info) throws Exception {
        AbstractHttpServer server = new AbstractHttpServer() {
            @Override
            public void config(ServerInfo serverInfo) {
                BeanCopy.beans(info,serverInfo);
            }
        };
        if(info.getInitStatus()==ServerInitStatus.START){
            server.start();
        }
        return server;
    }

    public MockServer getServer(String serverName){
        return this.servers.get(serverName);
    }

    public Set<ServerInfo> getServerInfos(){
        Set<ServerInfo> infos = Sets.newHashSet();
        for(MockServer server:this.servers.values()){
            infos.add(server.getServerInfo());
        }
        return infos;
    }


    public void addServer(String serverName,MockServer server){
        this.servers.put(serverName,server);
    }

    public boolean checkPortisUsed(int port){
        return isUsingPorts.contains(port);
    }

    public void addUsedPort(int port){
        this.isUsingPorts.add(port);
    }



}

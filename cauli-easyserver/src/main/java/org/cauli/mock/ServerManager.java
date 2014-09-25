package org.cauli.mock;

import com.google.common.collect.Maps;
import org.cauli.mock.admin.AdminServer;
import org.cauli.mock.context.Context;
import org.cauli.mock.context.ContextFactory;
import org.cauli.mock.data.DataProviderBuilder;
import org.cauli.mock.data.IDataProvider;
import org.cauli.mock.entity.KeyValueStore;
import org.cauli.mock.server.MockServer;
import org.cauli.mock.template.TemplateSourceBuilder;
import org.cauli.mock.template.TemplateSourceEngine;
import org.cauli.server.Configuration;

import java.util.Map;

/**
 * Created by tianqing.wang on 2014/7/7
 */
public class ServerManager {


    private boolean useAdmin;

    public int adminPort=-1;

    private Map<String,MockServer> servers= Maps.newHashMap();

    public ServerManager(boolean isUseAdmin){
        setAdminUsed(isUseAdmin);
    }

    //private Logger logger  = LoggerFactory.getLogger(ServerManager.class);

    public void init() throws Exception{
        this.servers=ServerBuilder.getInstance().getServers();
        if(useAdmin){
            if(adminPort==-1){
                AdminServer server = new AdminServer() {
                    @Override
                    public void configServer(Configuration configuration) {
                        configuration.setPort(9000);
                    }
                };
                server.start();
            }else{
                AdminServer server = new AdminServer() {
                    @Override
                    public void configServer(Configuration configuration) {
                        configuration.setPort(adminPort);
                    }
                };
                server.start();
            }

        }
    }

    public MockServer getMockServer(String serverName){
        return servers.get(serverName);
    }


    public java.util.Collection<MockServer> getAllServers(){
        return servers.values();
    }

    public void setAdminUsed(boolean isUsed){
        this.useAdmin=isUsed;
    }

    public void setTemplateSourceEngine(Class<? extends TemplateSourceEngine> engine){
        TemplateSourceBuilder.getInstance().setTemplateSourceEngineClass(engine);
    }

    public void setAdminPort(int port){
        this.adminPort=port;
    }

    public void setDataProvider(IDataProvider provider){
        DataProviderBuilder.getInstance().configDataProvider(provider);
    }

    public void addContext(String name,Object object){
        ServerBuilder.getInstance().addContext(name,object);
    }

    public void addContext(KeyValueStore store){
        ServerBuilder.getInstance().addContext(store.getKey(),store.getValue());
    }


}

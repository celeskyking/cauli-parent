package org.cauli.mock.server;


import org.cauli.mock.ServerInitStatus;
import org.cauli.mock.ServerProtocol;
import org.cauli.mock.ServerStatus;
import org.cauli.mock.action.MockAction;
import org.cauli.mock.context.Context;
import org.cauli.mock.entity.KeyValueStore;
import org.cauli.mock.entity.KeyValueStores;
import org.cauli.mock.entity.ServerInfo;

/**
 * @auther sky
 */
public interface MockServer<T extends MockAction> extends IServer{


    public void loadActions();

    public void start() throws Exception;

    public void stop();

    public void restart() throws Exception;

    public int getPort();

    public String getServerName();

    public ServerStatus getServerStatus();

    public ServerProtocol getProtocol();

    public java.util.Collection<T> getActions();

    public T getAction(String actionName);

    public ServerInitStatus getInitStatus();

    public T createAction(String actionName,String path,String templateValue);

    public T createAction(String actionName,String path,String returnStatus,String templateValue);

    public ServerInfo getServerInfo();

    public Context getContext();

    public void addContext(String name,Object object);

    public void addContext(KeyValueStore store);

}

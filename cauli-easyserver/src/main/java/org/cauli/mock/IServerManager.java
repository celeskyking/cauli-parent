package org.cauli.mock;

import org.cauli.common.keyvalue.KeyValueStore;
import org.cauli.mock.server.MockServer;

/**
 * @auther sky
 */
public interface IServerManager {

    public void init() throws Exception;


    public void addContext(String key,Object value);

    public void addContext(KeyValueStore keyValueStore);

    public MockServer getMockServer(String serverName);


}

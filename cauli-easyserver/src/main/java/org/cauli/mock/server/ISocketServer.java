package org.cauli.mock.server;


/**
 * Created by tianqing.wang on 2014/9/5
 */
public interface ISocketServer {

    public void start();

    public void stop();

    public void setRequestEncoding(String encoding);

    public void setResponseEncoding(String encoding);

}

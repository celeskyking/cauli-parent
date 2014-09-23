package org.cauli.server;

import com.google.common.io.Resources;

/**
 * Created by tianqing.wang on 2014/9/2
 */
public class Configuration {

    private String serverName;
    private int port=-1;
    private String keyStore;
    private String keyStorePassword;
    private String keyPassword;
    private String staticFile= Resources.getResource("public").getFile();
    private String viewPath;

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getKeyStore() {
        return keyStore;
    }

    public void setKeyStore(String keyStore) {
        this.keyStore = keyStore;
    }

    public String getKeyStorePassword() {
        return keyStorePassword;
    }

    public void setKeyStorePassword(String keyStorePassword) {
        this.keyStorePassword = keyStorePassword;
    }

    public String getKeyPassword() {
        return keyPassword;
    }

    public void setKeyPassword(String keyPassword) {
        this.keyPassword = keyPassword;
    }

    public String getStaticFile() {
        return staticFile;
    }

    public void setStaticFile(String staticFile) {
        this.staticFile = staticFile;
    }

    public String getViewPath() {
        return viewPath;
    }

    public void setViewPath(String viewPath) {
        this.viewPath = viewPath;
    }
}

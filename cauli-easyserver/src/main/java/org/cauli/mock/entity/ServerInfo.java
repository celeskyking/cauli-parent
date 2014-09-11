package org.cauli.mock.entity;


import org.cauli.mock.ServerInitStatus;
import org.cauli.mock.ServerProtocol;
import org.cauli.mock.ServerStatus;
import org.cauli.mock.constant.Constant;

import java.io.Serializable;

/**
 * Created by tianqing.wang on 2014/8/14
 */
public class ServerInfo implements Serializable{

    private String keyStore;
    private String keyStorePassword;
    private String keyPassword;
    private String ServerName;
    private boolean isAsyn;
    private int port=Constant.DEFAULT_PORT;
    private ServerStatus status;
    private ServerInitStatus initStatus=Constant.DEFAULT_INIT_STATUS;
    private ServerProtocol protocol;

    private String responseEncoding= Constant.DEFAULT_RESPONSE_ENCODING;

    private String requestEncoding=Constant.DEFAULT_REQUEST_ENCODING;

    public boolean isAsyn() {
        return isAsyn;
    }

    public void setAsyn(boolean isAsyn) {
        this.isAsyn = isAsyn;
    }

    public ServerStatus getStatus() {
        return status;
    }

    public void setStatus(ServerStatus status) {
        this.status = status;
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

    public String getServerName() {
        return ServerName;
    }

    public void setServerName(String serverName) {
        ServerName = serverName;
    }

    public ServerInitStatus getInitStatus() {
        return initStatus;
    }

    public void setInitStatus(ServerInitStatus initStatus) {
        this.initStatus = initStatus;
    }

    public ServerProtocol getProtocol() {
        return protocol;
    }

    public void setProtocol(ServerProtocol protocol) {
        this.protocol = protocol;
    }

    public String getResponseEncoding() {
        return responseEncoding;
    }

    public void setResponseEncoding(String responseEncoding) {
        this.responseEncoding = responseEncoding;
    }

    public String getRequestEncoding() {
        return requestEncoding;
    }

    public void setRequestEncoding(String requestEncoding) {
        this.requestEncoding = requestEncoding;
    }

    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{keyStore=[").append(keyStore)
            .append("],keyStorePassword=[").append(keyStorePassword)
            .append("],keyPassword=[").append(keyPassword)
            .append("],serverName=[").append(ServerName)
            .append("],isAysn=[").append(isAsyn)
            .append("],status=[").append(status)
            .append("],initStatus=[").append(initStatus)
            .append("],protocol=[").append(protocol)
            .append("],requestEncoding=").append(requestEncoding)
            .append("],responseEncoding=").append(responseEncoding).append("]");
        return stringBuilder.toString();
    }


}

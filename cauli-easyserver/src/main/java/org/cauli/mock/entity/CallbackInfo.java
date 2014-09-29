package org.cauli.mock.entity;

import org.cauli.mock.constant.Constant;
import org.cauli.server.HttpMethod;

import java.io.Serializable;
import java.nio.charset.Charset;

/**
 * Created by tianqing.wang on 2014/9/22
 */
public class CallbackInfo implements Serializable{

    public  class HttpInfo{
        private Charset queryEncoding;
        private Charset formEncoding;
        private String url;
        private HttpMethod method;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public HttpMethod getMethod() {
            return method;
        }

        public void setMethod(HttpMethod method) {
            this.method = method;
        }

        public Charset getQueryEncoding() {
            return queryEncoding;
        }

        public void setQueryEncoding(Charset queryEncoding) {
            this.queryEncoding = queryEncoding;
        }

        public Charset getFormEncoding() {
            return formEncoding;
        }

        public void setFormEncoding(Charset formEncoding) {
            this.formEncoding = formEncoding;
        }
    }


    public  class SocketInfo {
        private String host;
        private int port;
        private Charset charset;

        private boolean isAync;



        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public Charset getCharset() {
            return charset;
        }

        public void setCharset(Charset charset) {
            this.charset = charset;
        }

        public boolean isAync() {
            return isAync;
        }

        public void setAync(boolean isAync) {
            this.isAync = isAync;
        }
    }



    private String returnStatus= Constant.DEFAULT_RETURN_STATUS;

    public HttpInfo http=new HttpInfo();

    public SocketInfo socket=new SocketInfo();

    public String getReturnStatus() {
        return returnStatus;
    }

    public void setReturnStatus(String returnStatus) {
        this.returnStatus = returnStatus;
    }


}

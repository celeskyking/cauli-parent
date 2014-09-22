package org.cauli.mock.response;

import org.cauli.mock.entity.KeyValueStores;

/**
 * Created by tianqing.wang on 2014/9/19
 */
public class Response {

    private KeyValueStores keyValueStores;
    private String body;

    private String body(){
        return body;
    }

    public KeyValueStores getKeyValueStores() {
        return keyValueStores;
    }

    public void setKeyValueStores(KeyValueStores keyValueStores) {
        this.keyValueStores = keyValueStores;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}

package org.cauli.test;

import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import org.junit.Test;

/**
 * Created by tianqing.wang on 2014/8/25
 */
public class ClientTest {

    //@Test
    public void testPost(){
        HttpResponse response = HttpRequest.post("http://localhost:9999").form("name","tianqing.wang").send();
        System.out.println(response.bodyText());
    }
}

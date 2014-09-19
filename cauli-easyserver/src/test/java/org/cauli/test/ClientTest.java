package org.cauli.test;

import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import org.cauli.mock.ServerManager;
import org.junit.Test;

/**
 * Created by tianqing.wang on 2014/8/25
 */
public class ClientTest {

    public static void main(String[] args) throws Exception {
        new ServerManager(false).init();
    }
}

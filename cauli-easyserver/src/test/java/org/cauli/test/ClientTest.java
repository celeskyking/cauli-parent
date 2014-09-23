package org.cauli.test;

import org.cauli.mock.ServerManager;

/**
 * Created by tianqing.wang on 2014/8/25
 */
public class ClientTest {

    public static void main(String[] args) throws Exception {
        new ServerManager(false).init();
    }
}

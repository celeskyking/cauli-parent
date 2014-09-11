package org.cauli.mock;

import org.cauli.mock.exception.MockServerConstructError;
import org.cauli.mock.server.MockServer;

/**
 * @auther sky
 */
public class Validation {

    public static void checkMockServer(MockServer server) throws MockServerConstructError {
        try {
            server.getClass().getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            throw new MockServerConstructError("没有找到默认的MockServer构造方法:" + server.getClass().getName());
        }

    }
}

package org.cauli.mock.admin;

import org.cauli.server.CauliServer;
import org.cauli.server.router.Router;

/**
 * @auther sky
 */
public abstract class AdminServer extends CauliServer{
    @Override
    public void configRoute(Router router) {
        router.add("/admin",AdminController.class);
    }

}

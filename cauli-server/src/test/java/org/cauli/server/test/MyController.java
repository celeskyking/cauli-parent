package org.cauli.server.test;

import org.cauli.server.annotation.Path;
import org.cauli.server.controller.Controller;

/**
 * Created by tianqing.wang on 2014/9/2
 */
@Path("/admin")
public class MyController extends Controller{


    @Path("/HelloServer/{user}")
    public void hello() throws Exception {
        System.out.println("user->"+pathParam("user"));
        renderText(pathParam("user"));
    }


    @Override
    public void before() {

    }

    @Override
    public void after() {

    }
}

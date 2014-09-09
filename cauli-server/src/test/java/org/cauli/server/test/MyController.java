package org.cauli.server.test;

import org.cauli.server.annotation.Path;
import org.cauli.server.controller.Controller;

/**
 * Created by tianqing.wang on 2014/9/2
 */
public class MyController extends Controller{


    @Path("/hello/{user}")
    public void hello() throws Exception {
        String user = pathParam("user");
        data("name",user);
        renderFreemarker("index.ftl");
    }







}

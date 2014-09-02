package org.cauli.server.test;

import org.cauli.server.annotation.Path;
import org.cauli.server.controller.Controller;

/**
 * Created by tianqing.wang on 2014/9/2
 */
public class MyController extends Controller{


    @Path("/hello")
    public void hello() throws Exception {
        data("name","tianqing.wang");
        renderFreemarker("index.ftl");
    }





}

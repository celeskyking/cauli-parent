package org.cauli.server.test;

import org.cauli.server.controller.Controller;

/**
 * Created by tianqing.wang on 2014/9/25
 */

public class HelloController extends Controller{

    public void hello(){
        renderHtml("hello,world");
    }
}

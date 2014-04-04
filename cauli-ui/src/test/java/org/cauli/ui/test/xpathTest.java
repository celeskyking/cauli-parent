package org.cauli.ui.test;


import org.cauli.junit.anno.ThreadRunner;
import org.cauli.ui.runner.CauliUIRunner;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;


/**
 * Created by tianqing.wang on 14-3-31
 */
@RunWith(CauliUIRunner.class)
//@Retry(1)
//@Filter
@ThreadRunner(threads = 2)
//@Require(Engine.FIREFOX)
@Ignore
public class xpathTest {


    @Test
    //@Ignore
    //@Tag(release = "good")
    public void xpathTest(){
//        go("http://www.baidu.com");
//        $("#kw1").input(text/
//        $("#su1").click();
//        quit();
        System.out.println("test_first");
        //quit();
    }

    @Test
    //@Ignore
    //@Tag(release = "good")
    public void xpathTwo(){
        System.out.println("test");
        //quit();
    }
}

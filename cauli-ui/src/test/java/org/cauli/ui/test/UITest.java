package org.cauli.ui.test;

import org.cauli.ui.runner.CauliUIRunner;
import org.cauli.ui.selenium.browser.Engine;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.cauli.ui.selenium.browser.Auto.*;

/**
 * Created by tianqing.wang on 2014/9/10
 */
@RunWith(CauliUIRunner.class)
public class UITest {


    @Test
    public void testUI(){
        require(Engine.FIREFOX);
        go("http://www.baidu.com");
        page(BaiduPage.class).exec("百度搜索","北京");
        quit();
    }
}

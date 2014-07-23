package org.cauli.ui.test;


import org.cauli.junit.anno.Named;
import org.cauli.junit.anno.Param;
import org.cauli.junit.runner.CauliRunner;
import org.cauli.ui.selenium.browser.Engine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.Keys;

import static org.cauli.ui.selenium.browser.Auto.*;


/**
 * Created by tianqing.wang on 14-3-31
 */
@RunWith(CauliRunner.class)
//@Retry(1)
//@Filter
//@ThreadRunner(threads = 2)
//@Require(Engine.FIREFOX)
//@Ignore
public class xpathTest {


    @Test
    @Param
    public void xlsTest(@Named("result")int result){
        require(Engine.CHROME);
        go("http://codpay.beta.qunar.com/cod-web/fillOrder.do");
        currentPage().element("xpath->.//*[@id='loginForm']/div[2]/div[1]/input").input("18600574451");
        currentPage().element("xpath->.//*[@id='loginForm']/div[2]/div[2]/input").input("cmm821129");
        currentPage().element("xpath->.//*[@id='submit']").click();
        sleep(1);
        currentPage().element("xpath->.//*[@id='submit']").click();
        sleep(1);
        currentPage().element("xpath->.//*[@id='account-content']/div[2]/dl/dd[1]/div/div/div").click();
        currentPage().element("linkText->澳大利亚").click();
        currentPage().element("xpath->.//*[@id='money']").input("1234");
        currentPage().element("xpath->.//*[@id='nextBtn']").click();

        currentPage().element("#SWFUpload_0").input("C:\\Users\\tianqing.wang.QUNARSERVERS\\Pictures\\壁纸20140106193142.jpg");
//        sleep(3);
//        currentPage().keypress(Keys.TAB);
//        sleep(1);
//        currentPage().keypress(Keys.TAB);
//        sleep(1);
//        currentPage().keypress(Keys.TAB);
//        sleep(1);
//        currentPage().keypress(Keys.TAB);
//        sleep(1);
//        currentPage().keypress(Keys.TAB);
//        sleep(1);
//        currentPage().keypress(Keys.TAB);
//        sleep(1);
//        currentPage().keypress(Keys.TAB);
//        sleep(1);
//        currentPage().keypress(Keys.TAB);
//        sleep(1);
//        currentPage().keypress(Keys.ARROW_RIGHT);
//        sleep(1);
//        currentPage().keypress(Keys.ENTER);

    }


}

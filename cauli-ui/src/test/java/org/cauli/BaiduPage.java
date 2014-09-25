package org.cauli;

import org.cauli.ui.annotation.Commit;
import org.cauli.ui.annotation.Find;
import org.cauli.ui.selenium.browser.IBrowser;
import org.cauli.ui.selenium.element.Button;
import org.cauli.ui.selenium.element.CauliElement;
import org.cauli.ui.selenium.page.SourcePage;

/**
 * Created by tianqing.wang on 2014/9/25
 */

@Commit("百度首页")
public class BaiduPage extends SourcePage{


    @Find(value = "xpath->kw")
    private Button baiduInput;

    @Find("id->su")
    private CauliElement btn;


    public BaiduPage(IBrowser browser) throws Exception {
        super(browser);
    }



    public void search(String text){
        baiduInput.input(text);
        btn.click();
    }
}

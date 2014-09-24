package org.cauli.ui.test;

import org.cauli.ui.annotation.Commit;
import org.cauli.ui.annotation.Description;
import org.cauli.ui.selenium.browser.IBrowser;
import org.cauli.ui.selenium.page.SourcePage;

/**
 * Created by tianqing.wang on 2014/9/10
 */

@Commit(value = "百度首页",title = "百度一下，你就知道")
public class BaiduPage extends SourcePage{
    public BaiduPage(IBrowser browser) throws Exception {
        super(browser);
    }

    @Description("百度搜索")
    public void search(String text){
        $("#kw").input(text);
        $("#su").click();
    }
}

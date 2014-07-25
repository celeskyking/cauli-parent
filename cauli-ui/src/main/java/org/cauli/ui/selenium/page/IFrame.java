package org.cauli.ui.selenium.page;

import org.cauli.ui.selenium.browser.IBrowser;

/**
 * Created by tianqing.wang on 2014/7/25
 */
public abstract class IFrame extends Page{


    public IFrame(IBrowser browser) {
        super(browser);
    }

    public void toDefaultContent(){
        getBrowser().getCurrentBrowserDriver().switchTo().defaultContent();
    }
}

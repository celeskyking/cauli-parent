package org.cauli.ui.selenium.page;

import org.cauli.ui.selenium.browser.IBrowser;

/**
 * Created by celeskyking on 14-3-2
 */
public class Frame extends SourcePage{

    private String locate;

    public Frame(IBrowser browser) throws Exception {
        super(browser);
    }

    private String index;


    public String getLocate() {
        return locate;
    }

    public void setLocate(String locate) {
        this.locate = locate;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public void toDefautlContent(){
        getBrowser().getCurrentBrowserDriver().switchTo().defaultContent();
    }

}

package org.cauli.ui.selenium.browser;

/**
 * @author tianqing.wang
 */
public class BrowserManager {
    private IBrowser baseBrowser;
    public IBrowser getBrowser(){
        return baseBrowser;
    }
    public void setBrowser(IBrowser browser){
        this.baseBrowser=browser;
    }
}

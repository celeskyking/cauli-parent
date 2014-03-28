package org.cauli.ui.selenium.browser;



/**
 * Created with IntelliJ IDEA.
 * User: ouamaqing
 * Date: 13-5-30
 * Time: 上午10:46
 * To change this template use File | Settings | File Templates.
 */
public class WindowInfo {
    private String url;
    private String windowHandle;
    private String title;
    private IBrowser browser;

    public WindowInfo(IBrowser browser, String url, String windowHandle, String title) {
        this.browser = browser;
        this.url = url;
        this.windowHandle = windowHandle;
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public String getWindowHandle() {
        return windowHandle;
    }

    public String getTitle() {
        return title;
    }

    public IBrowser getBrowser() {
        return browser;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setWindowHandle(String windowHandle) {
        this.windowHandle = windowHandle;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBrowser(IBrowser browser) {
        this.browser = browser;
    }
}

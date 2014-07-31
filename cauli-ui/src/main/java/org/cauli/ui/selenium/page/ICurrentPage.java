package org.cauli.ui.selenium.page;


import org.cauli.ui.exception.FrameEnterException;
import org.cauli.ui.selenium.browser.IBrowser;
import org.cauli.ui.selenium.element.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
/**
 * @author tianqing.wang
 */
public interface ICurrentPage extends IPage {

    public IBrowser getBrowser();

    public Select select(String location);

    public void open(String url);
    
    public <T extends IElement>T element(Class<T> clazz);

    public IElement element(String id);

    public <T extends IElement>T element(Class<T> clazz, String id);

    public void assertAlert();

    public void assertTextNotPresent(String text) ;

    public void assertTitle(String title);

    public void assertTextPresent(String text);

    public String getPageSource();

    public String dealAlert();

    public String dealConfirm(boolean isyes);

    public String dealPrompt(boolean isyes, String text);

    public Object runJavaScript(String js, Object... objects);

    public Object runAsynJavaScript(String js, Object... objects);

    //public Set<String> getWindows();

    //public ICurrentPage selectLateOpenedWindow();

    public ICurrentPage openNewWindow(String url);

    public WebDriver getCurrentWindow();

    public void setBrowser(IBrowser browser);

    public <T> T frame(Class<T> clazz);

    public Frame frame(int index) throws FrameEnterException;

    public Frame frame(String locate) throws FrameEnterException;
    
    public IElement $(String jquery);

	void keypress(Keys key);

	void release();

    public Table table(String location);

    public CauliElements elements(String location);

    public void addCauliElements(CauliElements cauliElements);

    public void sleep(int seconds);


}

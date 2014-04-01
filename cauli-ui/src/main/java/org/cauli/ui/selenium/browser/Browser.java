package org.cauli.ui.selenium.browser;

import org.apache.commons.io.FileUtils;
import org.cauli.ui.selenium.listener.ActionListenerProxy;
import org.cauli.ui.selenium.page.CurrentPage;
import org.cauli.ui.selenium.page.ICurrentPage;
import org.cauli.ui.selenium.page.Page;
import org.cauli.ui.selenium.page.SourcePage;
import org.openqa.selenium.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 
 */
public class Browser implements IBrowser {
    private Logger logger = LoggerFactory.getLogger(Browser.class);
	private boolean isClosed=false;
    private boolean isUseJQuery=false;
    private WindowsCollectorListener windowsCollectorListener;
    private WindowSource windowSource;
    private Page page;
    private WebDriver driver;
    private Engine engine;
    public Browser(Engine browser){
        this.engine=browser;
        this.driver=browser.browser();
        maxWindow();
        this.driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
        this.windowSource=new WindowSource(this);
        this.windowsCollectorListener=new WindowsCollectorListener();
        this.windowSource.addWindowsListener(this.windowsCollectorListener);
        this.page=new CurrentPage(this);
        pageLoadTimeout(20);
        logger.info("初始化了浏览器"+browser.toString()+"来进行自动化测试");
    }

    public Browser(Engine browser,URL url){
        this.engine=browser;
        if(url==null){
            this.driver=browser.browser();
        }else{
            this.driver=browser.browser(url);
        }
        maxWindow();
        this.driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
        this.windowSource=new WindowSource(this);
        this.windowsCollectorListener=new WindowsCollectorListener();
        this.windowSource.addWindowsListener(this.windowsCollectorListener);
        this.page=new CurrentPage(this);
        pageLoadTimeout(20);
        logger.info("初始化了浏览器"+browser.toString()+"来进行自动化测试");
    }

    @Override
    public ICurrentPage open(String url) {
        this.getCurrentBrowserDriver().get(url);
        this.setClosed(false);
        //logger.info("打开了http地址"+url);
        this.page.setBrowser(this);
        this.windowSource.windowsCheck();
        logger.info("当前初始化页面信息：URL--->"+this.page().getUrl());
        logger.info("当前初始化页面信息：Title--->"+this.page().getTitle());
        logger.info("当前初始化页面信息：窗口句柄数--->"+this.getWindows().size());
        return this.page;
    }

    @Override
    public void maxWindow() {
        this.driver.manage().window().maximize();
    }

    @Override
    public void closeAllWindows() {
        ActionListenerProxy.getDispatcher().beforecloseAllWindows();
        if(this.driver!=null){
            this.getCurrentBrowserDriver().quit();
            this.setClosed(true);
            logger.info("关闭了浏览器");
        }else{
            logger.warn("与浏览器交互的session值可能已经中断了，请检查程序是否编写正确，程序还将继续运行下去");
        }
        ActionListenerProxy.getDispatcher().aftercloseAllWindows();
    }

    public Engine browserType(){
        return this.engine;
    }

    @Override
    public void back() {
        ActionListenerProxy.getDispatcher().beforeback();
        this.driver.navigate().back();
        logger.info("浏览器进行了后退操作");
        ActionListenerProxy.getDispatcher().afterback();
    }

    @Override
    public void refresh() {
        ActionListenerProxy.getDispatcher().beforerefresh();
        this.driver.navigate().refresh();
        logger.info("浏览器进行了刷新操作");
        ActionListenerProxy.getDispatcher().afterrefresh();
    }

    @Override
    public void forward() {
        ActionListenerProxy.getDispatcher().beforeforward();
        this.driver.navigate().forward();
        logger.info("浏览器进行了前进操作");
        ActionListenerProxy.getDispatcher().afterforward();
    }

    @Override
    public Set<String> getWindows() {
        return this.driver.getWindowHandles();
    }

    @Override
    public ICurrentPage selectDefaultWindow() {
        this.driver.switchTo().defaultContent();
        this.page.setBrowser(this);
        return this.page;
    }

    @Override
    public ICurrentPage selectFrame(By by) {
        this.driver.switchTo().frame(this.driver.findElement(by));
        this.page.setBrowser(this);
        return this.page;
    }

    @Override
    public ICurrentPage selectFrame(int index) {
        this.driver.switchTo().frame(index);
        this.page.setBrowser(this);
        return this.page;
    }

    @Override
    public ICurrentPage selectFrame(By by, int index) {
        this.driver.switchTo().frame(this.driver.findElements(by).get(index));
        this.page.setBrowser(this);
        return this.page;
    }

    @Override
    public ICurrentPage selectLastOpenedPage() {
        ActionListenerProxy.getDispatcher().beforeselectWindow();
        this.windowSource.getWindowsCollecter().updateWindows();
        String windowhandle = this.windowSource.getWindowsCollecter().getLastWindowhandle();
        this.driver.switchTo().window(windowhandle);
        this.page.setBrowser(this);
        ActionListenerProxy.getDispatcher().afterselectWindow();
        logger.info("当前页面信息：URL--->"+this.page().getUrl());
        logger.info("当前页面信息：Title--->"+this.page().getTitle());
        logger.info("当前页面信息：窗口句柄数--->"+this.getWindows().size());
        return this.page;
    }

    @Override
    public ICurrentPage selectWindowByTitle(String title) {
        ActionListenerProxy.getDispatcher().beforeselectWindow();
        this.windowSource.getWindowsCollecter().updateWindows();
        Set<String> windows = getCurrentBrowserDriver().getWindowHandles();
        for(String window : windows){
            getCurrentBrowserDriver().switchTo().window(window);
            String titleName = getCurrentBrowserDriver().getCurrentUrl();
            if(titleName.equals(title)){
                break;
            }
        }
        logger.info("当前页面切换到了-------->" + title);
        ActionListenerProxy.getDispatcher().afterselectWindow();
        this.page.setBrowser(this);
        logger.info("当前页面信息：URL--->"+this.page().getUrl());
        logger.info("当前页面信息：Title--->"+this.page().getTitle());
        logger.info("当前页面信息：窗口句柄数--->"+this.getWindows().size());
        return this.page;
    }

    @Override
    public ICurrentPage selectWindowByUrl(String url) {
        ActionListenerProxy.getDispatcher().beforeselectWindow();
        this.windowSource.getWindowsCollecter().updateWindows();
        Set<String> windows = getCurrentBrowserDriver().getWindowHandles();
        for(String window : windows){
            getCurrentBrowserDriver().switchTo().window(window);
            String titleName = getCurrentBrowserDriver().getCurrentUrl();
            if(titleName.equals(url)){
                break;
            }
        }
        ActionListenerProxy.getDispatcher().afterselectWindow();
        this.page.setBrowser(this);
        logger.info("当前页面信息：URL--->"+this.page().getUrl());
        logger.info("当前页面信息：Title--->"+this.page().getTitle());
        logger.info("当前页面信息：窗口句柄数--->"+this.getWindows().size());
        return this.page;
    }

    public ICurrentPage selectWindowByIndex(Integer index) {
        this.windowSource.getWindowsCollecter().updateWindows();
        String windowhandle=this.windowSource.getWindowsCollecter().getWindowhandleByIndex(index);
        this.driver.switchTo().window(windowhandle);
        logger.info("当前页面切换到了-------->" + this.driver.getTitle());
        this.page.setBrowser(this);
        logger.info("当前页面信息：URL--->"+this.page().getUrl());
        logger.info("当前页面信息：Title--->"+this.page().getTitle());
        logger.info("当前页面信息：窗口句柄数--->"+this.getWindows().size());
        return this.page;
    }

    @Override
    public ICurrentPage selectWindowContainsUrl(String url) {
        ActionListenerProxy.getDispatcher().beforeselectWindow();
        this.windowSource.getWindowsCollecter().updateWindows();
        Set<String> windows = getCurrentBrowserDriver().getWindowHandles();
        for(String window : windows){
            getCurrentBrowserDriver().switchTo().window(window);
            String titleName = getCurrentBrowserDriver().getCurrentUrl();
            if(titleName.contains(url)){
                break;
            }
        }
        this.page.setBrowser(this);
        logger.info("当前页面信息：URL--->"+this.page().getUrl());
        logger.info("当前页面信息：Title--->"+this.page().getTitle());
        logger.info("当前页面信息：窗口句柄数--->"+this.getWindows().size());
        ActionListenerProxy.getDispatcher().afterselectWindow();
        return this.page;
    }

    @Override
    public ICurrentPage currentPage() {
        if(this.page==null){
            this.page= new Page(this);
            this.page.setCurrentwindow(this.driver);
        }
        return page;
    }

    @Override
    public Page page() {
        if(this.page==null){
            this.page= new Page(this);
            this.page.setCurrentwindow(this.driver);
        }
        return page;
    }

    @Override
    public WebDriver getCurrentBrowserDriver() {
        return this.driver;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Object runJavaScript(String js, Object... objects) {
        ActionListenerProxy.getDispatcher().beforerunJS();
        Object obj= ((JavascriptExecutor)this.driver).executeScript(js,objects);
        ActionListenerProxy.getDispatcher().afterrunJS();
        return obj;
    }

    @Override
    public Object runAsynJavaScript(String js, Object... objects) {
        ActionListenerProxy.getDispatcher().beforerunJS();
        Object obj= ((JavascriptExecutor)this.driver).executeAsyncScript(js, objects);
        ActionListenerProxy.getDispatcher().afterrunJS();
        return obj;
    }

    public void takeScreetShot(String path){
        ActionListenerProxy.getDispatcher().beforetakeScreenShot();
        TakesScreenshot tss = (TakesScreenshot)this.driver;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmss");
        String time= sdf.format(new Date());
        File file = tss.getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(file,new File(path+File.separator+time+".png"));
            logger.info("浏览器当前页面截屏成功！截屏路径->"+path);
        } catch (IOException e) {
            logger.error("浏览器当前页面截屏失败！可能是因为文件路径不正确");
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        ActionListenerProxy.getDispatcher().aftertakeScreenShot();

    }

    public boolean isClosed() {
        return isClosed=false;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    public WindowSource getWindowSource() {
        return windowSource;
    }
    
    protected void setWebDriver(WebDriver driver){
    	this.driver=driver;
    }

	@Override
	public void pageLoadTimeout(int seconds) {
		this.driver.manage().timeouts().pageLoadTimeout(seconds, TimeUnit.SECONDS);	
	}

    public <T extends SourcePage> T waitFor(T page){
        String js ="\n"+
                "if(document.readyState==\"complete\"){\n" +
                "   return \"down\"  \n"+
                "}\n" ;
        String text = (String) runJavaScript(js);
        for(int i=0;i<10;i++){
            if("down".equals(text)){
                return page;
            } else{
                currentPage().sleep(1);
                continue;
            }
        }
        return null;

    }


    
    public void elementLoadTimeout(int seconds){
    	this.driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
    }

    @SuppressWarnings("unchecked")
	public <T extends SourcePage> T page(Class<T> pageClass) {
        try {
            @SuppressWarnings("rawtypes")
			Constructor constructor=pageClass.getConstructor(IBrowser.class);
            T page =  (T) constructor.newInstance(this);
            return waitFor(page);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InvocationTargetException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InstantiationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return null;
    }

    @Override
    public void openNew(String url) {
        runJavaScript("window.open('"+url+"')");
        selectLastOpenedPage();
    }

    @Override
    public boolean isUseJQuery() {
        return isUseJQuery;
    }


    @Override
	public ICurrentPage selectWindowContainsTitle(String title) {
        ActionListenerProxy.getDispatcher().beforeselectWindow();
        this.windowSource.getWindowsCollecter().updateWindows();
        Set<String> windows = getCurrentBrowserDriver().getWindowHandles();
        for(String window : windows){
            getCurrentBrowserDriver().switchTo().window(window);
            String titleName = getCurrentBrowserDriver().getCurrentUrl();
            if(titleName.contains(title)){
                break;
            }
        }
        logger.info("当前页面切换到了-------->" + title);
        ActionListenerProxy.getDispatcher().afterselectWindow();
        this.page.setBrowser(this);
        logger.info("当前页面信息：URL--->"+this.page().getUrl());
        logger.info("当前页面信息：Title--->"+this.page().getTitle());
        logger.info("当前页面信息：窗口句柄数--->"+this.getWindows().size());
        return this.page;
	}


    public void setUseJQuery(boolean isUseJQuery) {
        this.isUseJQuery = isUseJQuery;
    }
}

package org.cauli.ui.selenium.page;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.cauli.ui.exception.FrameEnterException;
import org.cauli.ui.selenium.PageHelper;
import org.cauli.ui.selenium.browser.IBrowser;
import org.cauli.ui.selenium.element.*;
import org.cauli.ui.selenium.element.Select;
import org.cauli.ui.selenium.listener.ActionListenerProxy;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class Page implements ICurrentPage {
    private Logger logger = LoggerFactory.getLogger(Page.class);
    private String commit;
    private Map<String,CauliElement> cauliElementMap=Maps.newHashMap();
    private Map<String,CauliElements> elementsMap=Maps.newHashMap();
    private Map<String,Class<? extends Frame>> frameMap = Maps.newHashMap();

    private Map<String,Class<? extends SubPage>> subPageMap = Maps.newHashMap();

    private Actions actions;
    public WebDriver getCurrentwindow() {
        return currentwindow;
    }

    public Map<String, CauliElement> getCauliElementMap() {
        return cauliElementMap;
    }

    public void addCauliElement(CauliElement cauliElement) {
        this.cauliElementMap.put(cauliElement.getId(),cauliElement);
    }

    public void initFrameAndSubPage(){
        frameMap.putAll(PageHelper.getInstance().getFrameMap());
        subPageMap.putAll(PageHelper.getInstance().getSubPageMap());
    }



    public String getCommit() {
        return commit;
    }

    public void setCommit(String commit) {
        this.commit = commit;
    }

    private WebDriver currentwindow;

    public IBrowser getBrowser() {
        return browser;
    }

    private IBrowser browser;
    public Page(IBrowser browser){
        this.browser=browser;
        this.currentwindow=browser.getCurrentBrowserDriver();
        this.actions=new Actions(getCurrentwindow());
        initFrameAndSubPage();
    }

    public Page(WebDriver driver){
        this.currentwindow=driver;
    }

    public void setBrowser(IBrowser browser){
        this.browser=browser;
        this.currentwindow=browser.getCurrentBrowserDriver();
    }

    public <T> T frame(Class<T> clazz) {
        try {
            return clazz.getDeclaredConstructor(ICurrentPage.class).newInstance(this);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Frame frame(int index) throws FrameEnterException {
        try {
            return new Frame(this.browser,this,index);
        } catch (Exception e) {
            logger.error("切换Frame[索引:{}]失败,异常信息:{}", index, e);
            throw new FrameEnterException("无法切入Frame,索引:"+index,e);
        }
    }

    @Override
    public Frame frame(String locate) throws FrameEnterException {
        try {
            return new Frame(this.browser,this,locate);
        } catch (Exception e) {
            logger.error("切换Frame[{}]失败,异常信息:{}",locate,e);
            throw new FrameEnterException("无法切入Frame"+locate,e);
        }
    }


    @Override
    public Set<Cookie> getAllCookies() {
        return this.currentwindow.manage().getCookies();
    }

    @Override
    public void deleteAllCookies() {
        this.currentwindow.manage().deleteAllCookies();
        logger.info("["+this.commit+"]进行了删除所有cookie的操作");
    }

    @Override
    public String getTitle() {
        return this.currentwindow.getTitle();
    }

    @Override
    public String getUrl() {
        return this.currentwindow.getCurrentUrl();
    }

    @Override
    public String getCookieByName(String name) {
        return this.currentwindow.manage().getCookieNamed(name).getValue();
    }

	public static <T> T page(Class<T> clazz) {
        try {
            T currentpage= (T) Class.forName(clazz.getName()).newInstance();
            return currentpage;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("没有找到这个class类"+clazz.getName()+",请检查类是否被加载或者类名是否正确",e);
        }
    }


    @Override
    public void open(String url) {
        this.currentwindow.get(url);
        logger.info("["+this.commit+"]页面跳转到了页面"+url);
    }

    @Override
    public <T extends IElement> T element(Class<T> clazz) {
        try {
            Constructor constructor = clazz.getConstructor(getBrowser().getClass());
            return (T) constructor.newInstance(getBrowser());
        } catch (Exception e) {
            e.printStackTrace();
            throw new NoSuchElementException("没有找到此类型的元素:"+clazz.getName(),e) ;
        }
    }


    public Select select(String location){
        Select select;
        if(location.startsWith("@")){
            select = new Select(getBrowser());
            select.setSelectElement(cauliElementMap.get(StringUtils.substringBefore(location,"@")).getElement());
        }else{
            select = new Select(getBrowser(),location);
        }

        return select;
    }

    protected Map<String, CauliElements> getElementsMap() {
        return elementsMap;
    }

    protected void setElementsLists(Map<String, CauliElements> elementsLists) {
        this.elementsMap = elementsLists;
    }

    @Override
    public IElement element(String location) {
        if(StringUtils.startsWith(location,"@")){
            String id = StringUtils.substringAfter(location,"@");
            if(this.cauliElementMap.get(id)!=null){
                return getCauliElementMap().get(id);
            }
        }else{
            return new CauliElement(getBrowser(),location);
        }

        return null;
        
    }

    @Override
    public void assertAlert() {
        try{
            this.browser.getCurrentBrowserDriver().switchTo().alert();
            logger.info("当前页面找到了alert，校验成功！");
        }catch (NoAlertPresentException e){
            logger.error("当前页面没有找到了alert，校验失败！");
            Assert.fail();
        }
    }

    @Override
    public void assertTextNotPresent(String text) {
        if(getPageSource().contains(text)){
            logger.info("当前页面找到指定的内容，校验成功！");
        }else{
            logger.error("当前页面没有找到指定内容"+text+"，校验失败！");
            Assert.fail();
        }
    }

    @Override
    public void assertTitle(String title) {
        if(getTitle().equals(title)){
            logger.info("当前页面的title值["+title+"]校验成功，校验成功！");
        }else{
            logger.error("当前页面title值["+title+"]校验失败，断言失败！");
            Assert.fail();
        }
    }

    @Override
    public void assertTextPresent(String text) {
        if(getPageSource().contains(text)){
            logger.info("当前页面没有找到指定的内容，校验成功！");
        }else{
            logger.error("当前页面找到了指定内容"+text+"，校验失败！");
            Assert.fail();
        }
    }

    @Override
    public String getPageSource() {
        return this.currentwindow.getPageSource();
    }


    @Override
    public String dealAlert() {
        ActionListenerProxy.getDispatcher().beforedealAlert();
        String alerMessage=null;
        try{
            Alert alert=this.browser.getCurrentBrowserDriver().switchTo().alert();
            alerMessage=alert.getText();
            alert.accept();
            ActionListenerProxy.getDispatcher().afterdealAlert();
            return alerMessage;
        }catch(Exception e){
            ActionListenerProxy.getDispatcher().afterdealAlert();
            logger.warn("["+this.commit+"]没有找到alert窗口，程序将继续运行，可能会出现异常，请查看代码是否正确");
            return null;
        }
    }

    @Override
    public String dealConfirm(boolean isyes) {
        ActionListenerProxy.getDispatcher().beforedealConfirm();
        String alerMessage=null;
        try{
            Alert alert=this.browser.getCurrentBrowserDriver().switchTo().alert();
            alerMessage=alert.getText();
            if(isyes){
                alert.accept();
            }else{
                alert.dismiss();
            }
            ActionListenerProxy.getDispatcher().afterdealConfirm();
            return alerMessage;
        }catch(Exception e){
            ActionListenerProxy.getDispatcher().afterdealConfirm();
            logger.warn("["+this.commit+"]没有找到comfirm窗口，程序将继续运行，可能会出现异常，请查看代码是否正确");
            return null;
        }
    }

    @Override
    public String dealPrompt(boolean isyes, String text) {
        ActionListenerProxy.getDispatcher().beforedealPrompt();
        String alerMessage=null;
        try{
            Alert alert=this.browser.getCurrentBrowserDriver().switchTo().alert();
            alert.sendKeys(text);
            alerMessage=alert.getText();
            if(isyes){
                alert.accept();
            }else{
                alert.dismiss();
            }
            ActionListenerProxy.getDispatcher().afterdealPrompt();
            return alerMessage;
        }catch(Exception e){
            ActionListenerProxy.getDispatcher().afterdealPrompt();
            logger.warn("["+this.commit+"]没有找到prompt窗口，程序将继续运行，可能会出现异常，请查看代码是否正确");
            return null;
        }
    }

    @Override
    public Object runJavaScript(String js, Object... objects) {
        return ((JavascriptExecutor)this.currentwindow).executeScript(js, objects);
    }

    @Override
    public Object runAsynJavaScript(String js, Object... objects) {
        return ((JavascriptExecutor)this.currentwindow).executeAsyncScript(js, objects);
    }

    @Override
    public ICurrentPage openNewWindow(String url) {
        runJavaScript("window.open(\""+url+"\")");
        logger.info("在新的窗口打开了链接"+url);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.browser.selectLastOpenedPage();
        return this.browser.currentPage();
    }

    @Override
    public WebDriver getCurrentWindow() {
        return this.browser.getCurrentBrowserDriver();
    }



    public void setCurrentwindow(WebDriver currentwindow) {
        this.currentwindow = currentwindow;
    }



	@Override
	public <T extends IElement> T element(Class<T> clazz, String location) {
		try {
            if(StringUtils.startsWith(location,"@")){
                String id = StringUtils.substringAfter(location, "@");
                CauliElement element = getCauliElementMap().get(id);
                if(element!=null){
                    Constructor<T> ctor1=clazz.getConstructor(IBrowser.class);
                    T t= ctor1.newInstance(getBrowser());
                    t.setElement(element.getElement());
                }
            }else {
                Constructor<T> ctor = clazz.getConstructor(IBrowser.class,String.class);
                return ctor.newInstance(getBrowser(),location);
            }

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	public IElement $(String jquery) {
		CauliElement cauliElement= new CauliElement(browser,jquery);
        cauliElement.setId(jquery);
        return  cauliElement;
	}


    
    @Override
    public void keypress(Keys key) {
            this.actions.sendKeys(key).perform();
            
    }

    @Override
    public void release() {
            this.actions.release().build().perform();
            
    }

    @Override
    public Table table(String location) {
        Table table=null;
        if(StringUtils.startsWith(location,"@")){
            String id=StringUtils.substringAfter(location,"@");
            if(getCauliElementMap().get(id)!=null){
                 table=new Table(getBrowser(), getCauliElementMap().get(id));
            }

        }else{
            table = new Table(getBrowser(),location);
        }

        return table;
    }

    @Override
    public CauliElements elements(String location) {
        CauliElements cauliElements=null;
        if(StringUtils.startsWith(location,"@")){
            String id=StringUtils.substringAfter(location,"@");
            if(getCauliElementMap().get(id)!=null){
                cauliElements=elementsMap.get(location);
            }

        }else{
            cauliElements = new CauliElements(getBrowser(),location);
        }

        return cauliElements;
    }

    @Override
    public void addCauliElements(CauliElements cauliElements) {
        this.elementsMap.put(cauliElements.getId(),cauliElements);
    }





    public void sleep(int seconds){
        try {
            Thread.sleep(seconds*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}


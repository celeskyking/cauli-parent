package org.cauli.ui.selenium.element;

import org.apache.commons.io.IOUtils;
import org.cauli.ui.selenium.browser.IBrowser;
import org.cauli.ui.selenium.listener.ActionListener;
import org.cauli.ui.selenium.listener.ActionListenerProxy;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 *@author 王天庆
 * 这是一个元素类，单纯封装的一个元素类
 */
public class CauliElement implements IElement {
    private Logger logger = LoggerFactory.getLogger(CauliElement.class);
    private IBrowser browser;
    private WebElement element;
    private Actions actions;
    private String id;
    private String Locate;

    public String getLocate() {
        return Locate;
    }

    public void setLocate(String locate) {
        Locate = locate;
    }

    public CauliElement(IBrowser browser){
        this.browser=browser;
        actions=new Actions(this.getBrowser().getCurrentBrowserDriver());
        this.id="Element";
        this.element=null;
    }

    public CauliElement(IBrowser browser,String location){
        this(browser);
        try{
            WebDriver driver = this.getBrowser().getCurrentBrowserDriver();
            if(this.browser.isUseJQuery()){
                if(location.contains("->")){
                    By by = LocationParse.parseLocation(location,driver.getPageSource());
                    this.element=driver.findElement(by);
                    this.id= by.toString();
                }else{
                    this.element = jquery(location);
                    this.id=location;
                }
            }else{
                this.element=driver.findElement(LocationParse.parseLocation(location,driver.getPageSource()));
            }
        }catch (Exception e){
            this.element=null;
        }


    }

    protected WebElement jquery(String jquery){
        InputStream inputStream=getClass().getClassLoader().getResourceAsStream("jquery.js");
        String jqueryJs = null;
        try {
            jqueryJs = IOUtils.toString(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        WebDriver driver = getBrowser().getCurrentBrowserDriver();
        return (WebElement)( (JavascriptExecutor)driver).executeScript(jqueryJs+"return jQuery(\"" + jquery + "\")[0]");
    }


    @Override
    public IElement find(String location) {
        WebDriver driver = getBrowser().getCurrentBrowserDriver();
        try {
            if(this.element==null){
                if(this.browser.isUseJQuery()){
                    if(location.contains("->")){
                        By by = LocationParse.parseLocation(location,driver.getPageSource());
                        this.element=driver.findElement(by);
                        this.id= by.toString();
                    }else{
                        this.element = jquery(location);
                        this.id=location;
                    }
                }else{

                    this.element=driver.findElement(LocationParse.parseLocation(location,driver.getPageSource()));
                    this.id=location;
                }
            }else{
                if(this.browser.isUseJQuery()){
                    if(location.contains("->")){
                        By by = LocationParse.parseLocation(location,driver.getPageSource());
                        this.element=getElement().findElement(by);
                        this.id= by.toString();
                    }else{
                        this.element = jquery(location);
                        this.id=location;
                    }
                }else{
                    this.element=getElement().findElement(LocationParse.parseLocation(location, driver.getPageSource()));
                    this.id=location;
                }
            }

        }catch (NoSuchElementException e){
            this.element=null;
        }
    return this;

    }

    @Override
    public <T> T find(String location, Class<T> tClass) {
        WebDriver driver = getBrowser().getCurrentBrowserDriver();
        try {
            if(this.element==null){
                if(this.browser.isUseJQuery()){
                    if(location.contains("->")){
                        By by = LocationParse.parseLocation(location,driver.getPageSource());
                        this.element=driver.findElement(by);
                        this.id= by.toString();
                    }else{
                        this.element = jquery(location);
                        this.id=location;
                    }
                }else{

                    this.element=driver.findElement(LocationParse.parseLocation(location,driver.getPageSource()));
                }
            }else{
                if(this.browser.isUseJQuery()){
                    if(location.contains("->")){
                        By by = LocationParse.parseLocation(location,driver.getPageSource());
                        this.element=getElement().findElement(by);
                        this.id= by.toString();
                    }else{
                        this.element = jquery(location);
                        this.id=location;
                    }
                }else{
                    this.element=getElement().findElement(LocationParse.parseLocation(location,driver.getPageSource()));
                }
            }

        }catch (NoSuchElementException e){
            this.element=null;
        }
        try {
            Constructor<T> constructor = tClass.getConstructor(IBrowser.class,String.class);
            return constructor.newInstance(getBrowser(),location);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void click() {
        ActionListenerProxy.getDispatcher().beforeClickOn();
        if(isExist()){
            element.click();
            logger.info("["+id+"]点击操作成功");
        }else{
            logger.error("["+id+"]元素查找失败，可能这个元素不存在，点击失败！");
            throw new NoSuchElementException("["+id+"]元素查找失败，可能这个元素不存在，点击失败！");
        }
        this.browser.getWindowSource().windowsCheck();
        ActionListenerProxy.getDispatcher().afterClickOn();
    }

    @Override
    public void doubleClick() {
        ActionListenerProxy.getDispatcher().beforedoubleClick();
        if(isExist()){
            actions.doubleClick().build().perform();
            logger.info("["+id+"]双击操作成功");
        }else{
            logger.error("["+id+"]元素查找失败，可能这个元素不存在，双击击失败！");
            throw new NoSuchElementException("["+id+"]元素查找失败，可能这个元素不存在，双击失败！");
        }
        this.browser.getWindowSource().windowsCheck();
        ActionListenerProxy.getDispatcher().afterdoubleClick();

    }

    @Override
    public void keyDown(Keys key) {
        ActionListenerProxy.getDispatcher().beforekeyDown();
        if(isExist()){
            actions.keyDown(key).build().perform();
            logger.info("["+id+"]按下键盘按钮["+key+"]成功");
        }else{
            logger.error("["+id+"]元素查找失败，可能这个元素不存在，按下按键["+key+"]失败！");
            throw new NoSuchElementException("["+id+"]元素查找失败，可能这个元素不存在，按下按钮["+key+"]失败！");
        }
        this.browser.getWindowSource().windowsCheck();
        ActionListenerProxy.getDispatcher().afterkeyDown();
    }

    @Override
    public void keyUp(Keys key) {
        ActionListenerProxy.getDispatcher().beforekeyUp();
        if(isExist()){
            actions.keyDown(key).build().perform();
            logger.info("["+id+"]按下键盘按钮"+key+"成功");
        }else{
            logger.error("["+id+"]元素查找失败，可能这个元素不存在，松开按键"+key+"失败！");
            throw new NoSuchElementException("["+id+"]元素查找失败，可能这个元素不存在，松开按键"+key+"失败！");
        }
        this.browser.getWindowSource().windowsCheck();
        ActionListenerProxy.getDispatcher().afterkeyUp();
    }

    @Override
    public void assertAttribute(String attr,String value) {
        if(getAttribute(attr).equals(value)){
            logger.info("["+id+"]这个元素的属性["+attr+"]的值[="+value+"]断言正确");
        }else{
            logger.error("["+id+"]这个元素的属性["+attr+"]的值[="+value+"]断言失败");
            Assert.fail();
        }
    }

    @Override
    public void assertEditable() {
        if(isEnable()){
            logger.info("["+id+"]这个元素可编辑的断言成功");
        }else{
            logger.error("["+id+"]这个元素可编辑性断言失败");
            Assert.fail();
        }
    }

    @Override
    public void assertNotEditable() {
        if(!isEnable()){
            logger.info("["+id+"]这个元素不可编辑的断言成功");
        }else{
            logger.error("["+id+"]这个元素不可编辑性断言失败");
            Assert.fail();
        }
    }

    @Override
    public void assertSelected() {
        if(isSelected()){
            logger.info("["+id+"]这个元素可选择性断言成功");
        }else{
            logger.error("["+id+"]这个元素可选择性断言失败");
            Assert.fail();
        }
    }

    @Override
    public void assertIsExist() {
        if(isExist()){
            logger.info("["+id+"]这个元素存在性断言成功");
        }else{
            logger.error("["+id+"]这个元素存在性断言失败");
            Assert.fail();
        }
    }

    @Override
    public void assertText(String text) {
        if(getText().equals(text)){
            logger.info("["+id+"]这个元素的文本值["+text+"]断言成功");
        }else{
            logger.error("["+id+"]这个元素的文本值["+text+"]断言失败");
            Assert.fail();
        }
    }

    @Override
    public void assertValue(String value) {
        if(getAttribute("value").equals(value)){
            logger.info("["+id+"]这个元素的value值["+value+"]断言成功");
        }else{
            logger.error("["+id+"]这个元素的value值["+value+"]断言失败");
            Assert.fail();
        }
    }

    @Override
    public IElement clear() {
        ActionListenerProxy.getDispatcher().beforeclear();
        if(isExist()){
            element.clear();
            logger.info("["+id+"]清空操作成功");

        }else{
            logger.error("[" + id + "]元素查找失败，可能这个元素不存在，清空失败！");
            throw new NoSuchElementException("["+id+"]元素查找失败，可能这个元素不存在，清空失败！");
        }
        ActionListenerProxy.getDispatcher().afterclear();
        return this;
    }

    @Override
    public void input(String text) {
        ActionListenerProxy.getDispatcher().beforeSendkeys();
        if(isExist()){
            element.sendKeys(text);
            logger.info("["+id+"]输入["+text+"]值操作成功");

        }else{
            logger.error("["+id+"]元素查找失败，可能这个元素不存在，输入["+text+"]失败！");
            throw new NoSuchElementException("["+id+"]元素查找失败，可能这个元素不存在，输入["+text+"]失败！");
        }
        ActionListenerProxy.getDispatcher().afterSendkeys();
    }

    @Override
    public void focus() {
        this.element.sendKeys("");
        this.browser.getWindowSource().windowsCheck();
    }

    @Override
    public String getAttribute(String attr) {
        if(isExist()){
            String attrvalue = this.element.getAttribute(attr);
            logger.info("["+id+"]获取属性[attr]的值[="+attrvalue+"]成功操作成功");
            return attrvalue;
        }else{
            logger.error("["+id+"]元素查找失败，可能这个元素不存在，获取属性"+attr+"失败！");
            throw new NoSuchElementException("["+id+"]元素查找失败，可能这个元素不存在，获取属性["+attr+"]的值失败！");
        }
    }

    @Override
    public String getText() {
        if(isExist()){
            String text=this.element.getText();
            logger.info("["+id+"]获取文本值["+text+"]操作成功");
            return text;
        }else{
            logger.error("["+id+"]元素查找失败，可能这个元素不存在，获取文本值失败！");
            throw new NoSuchElementException("["+id+"]元素查找失败，可能这个元素不存在，获取文本值失败！");
        }
    }

    @Override
    public String getCssValue(String name) {
        if(isExist()){
            String cssvalue = this.element.getCssValue(name);
            logger.info("["+id+"]元素获取css["+name+"]的值[="+cssvalue+"]操作成功");
            return cssvalue;
        }else{
            logger.error("["+id+"]元素查找失败，可能这个元素不存在，获取Css["+name+"]值失败！");
            throw new NoSuchElementException("["+id+"]元素查找失败，可能这个元素不存在，获取css["+name+"]失败！");
        }
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id=id;
    }

    @Override
    public Point getLocation() {
        if(isExist()){
            Point point=this.element.getLocation();
            logger.info("["+id+"]获取元素位置操作成功");
            return point;
        }else{
            logger.error("["+id+"]元素查找失败，可能这个元素不存在，获取位置失败！");
            throw new NoSuchElementException("["+id+"]元素查找失败，可能这个元素不存在，获取位置失败！");
        }
    }


    @Override
    public int[] getSize() {
        if(isExist()){
            Dimension ds= this.element.getSize();
            int[] size=new int[]{ds.getHeight(),ds.getWidth()};
            logger.info("["+id+"]获取元素大小操作成功");
            return size;
        }else{
            logger.error("["+id+"]元素查找失败，可能这个元素不存在，获取元素大小失败！");
            throw new NoSuchElementException("["+id+"]元素查找失败，可能这个元素不存在，获取元素大小失败！");
        }
    }

    @Override
    public String getTagName() {
        if(isExist()){
            String tagname=this.element.getTagName();
            logger.info("["+id+"]获取元素标签名["+tagname+"]操作成功");
            return tagname;
        }else{
            logger.error("["+id+"]元素查找失败，可能这个元素不存在，获取元素标签名失败！");
            throw new NoSuchElementException("["+id+"]元素查找失败，可能这个元素不存在，获取元素标签名失败！");
        }
    }

    @Override
    public void scroll() {
        ActionListenerProxy.getDispatcher().beforescroll();
        if(isExist()){
            Point point=getLocation();
            int x=point.getX();
            int y=point.getY();
            this.browser.runJavaScript("window.scrollTo("+x+","+y+")");
            logger.info("["+id+"]视角移动到了["+x+","+y+"]的位置");
        }else{
            logger.error("["+id+"]元素查找失败，可能这个元素不存在，移动视角失败！");
            throw new NoSuchElementException("["+id+"]元素查找失败，可能这个元素不存在，移动视角失败！");
        }
        ActionListenerProxy.getDispatcher().afterscroll();
    }

    @Override
    public void mouseOver() {
        ActionListenerProxy.getDispatcher().beforeMouseOver();
        if(isExist()){
            this.actions.moveToElement(this.element).build().perform();
            logger.info("["+id+"]元素处鼠标悬浮成功！");
        }else{
            logger.error("["+id+"]元素查找失败，可能这个元素不存在，鼠标悬浮失败！");
            throw new NoSuchElementException("["+id+"]元素查找失败，可能这个元素不存在，鼠标悬浮失败！");
        }
        this.browser.getWindowSource().windowsCheck();
        ActionListenerProxy.getDispatcher().afterMouseOver();
    }

    @Override
    public void submit() {
        ActionListenerProxy.getDispatcher().beforesubmit();
        if(isExist()){
            this.element.submit();
            logger.info("["+id+"]元素提交表单成功！");
        }else{
            logger.error("["+id+"]元素查找失败，可能这个元素不存在，提交表单失败！");
            throw new NoSuchElementException("["+id+"]元素查找失败，可能这个元素不存在，提交表单失败！");
        }
        this.browser.getWindowSource().windowsCheck();
        ActionListenerProxy.getDispatcher().aftersubmit();
    }

    @Override
    public boolean isDisplay() {
        if(isExist()){
            return this.element.isDisplayed();
        }else{
            logger.error("["+id+"]元素查找失败，可能这个元素不存在,判断元素是否可见失败！");
            throw new NoSuchElementException("["+id+"]元素查找失败，可能这个元素不存在，判断元素是否可见失败！");
        }
    }

    @Override
    public boolean isEnable() {
        if(isExist()){
            return this.element.isEnabled();
        }else{
            logger.error("["+id+"]元素查找失败，可能这个元素不存在，判断元素是否可编辑失败！");
            throw new NoSuchElementException("["+id+"]元素查找失败，可能这个元素不存在，判断元素是否可编辑失败！");
        }
    }


    @Override
    public boolean isSelected() {
        if(isExist()){
            return this.element.isSelected();
        }else{
            logger.error("["+id+"]元素查找失败，可能这个元素不存在，判断元素是否被选择失败！");
            throw new NoSuchElementException("["+id+"]元素查找失败，可能这个元素不存在，判断元素是否被选择失败！");
        }
    }

    @Override
    public void dragAndDrop(IElement e) {
        ActionListenerProxy.getDispatcher().beforedragAndDrop();
        if(isExist()){
            this.actions.dragAndDropBy(this.element,e.getLocation().getX(),e.getLocation().getY()).build().perform();
            logger.info("["+id+"]元素拖拽成功");
        }else{
            logger.error("["+id+"]元素查找失败，可能这个元素不存在，元素拖拽失败！");
            throw new NoSuchElementException("["+id+"]元素查找失败，可能这个元素不存在，元素拖拽失败！");
        }
        this.browser.getWindowSource().windowsCheck();
        ActionListenerProxy.getDispatcher().afterdragAndDrop();
    }

    @Override
    public void dragAndDrop(Point point) {
        ActionListenerProxy.getDispatcher().beforedragAndDrop();
        if(isExist()){
            this.actions.dragAndDropBy(this.element,point.getX(),point.getY());
            logger.info("["+id+"]元素拖拽成功");
        }else{
            logger.error("["+id+"]元素查找失败，可能这个元素不存在，元素拖拽失败！");
            throw new NoSuchElementException("["+id+"]元素查找失败，可能这个元素不存在，元素拖拽失败！");
        }
        this.browser.getWindowSource().windowsCheck();
        ActionListenerProxy.getDispatcher().afterdragAndDrop();
    }

    @Override
    public void leftDown() {
        ActionListenerProxy.getDispatcher().beforeleftDown();
        if(isExist()){
            this.actions.clickAndHold(this.element).build().perform();
            logger.info("["+id+"]元素处按住左键");
        }else{
            logger.error("["+id+"]元素查找失败，可能这个元素不存在，元素处按住左键失败！");
            throw new NoSuchElementException("["+id+"]元素查找失败，可能这个元素不存在，元素处按住左键失败！");
        }
        this.browser.getWindowSource().windowsCheck();
        ActionListenerProxy.getDispatcher().afterleftDown();
    }

    @Override
    public void leftUp() {
        ActionListenerProxy.getDispatcher().beforeleftUp();
        if(isExist()){
            this.actions.release(this.element).build().perform();
            logger.info("["+id+"]元素处松开左键");
        }else{
            logger.error("["+id+"]元素查找失败，可能这个元素不存在，元素处松开左键失败！");
            throw new NoSuchElementException("["+id+"]元素查找失败，可能这个元素不存在，元素处按住松开失败！");
        }
        this.browser.getWindowSource().windowsCheck();
        ActionListenerProxy.getDispatcher().afterleftUp();
    }


    public boolean isExist(){
        if(this.element==null){
            for(int i=0;i<10;i++){
                if(this.element==null){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    continue;
                }else {
                    locateInit();
                }
            }
            if(this.element==null){
                return false;
            }else {
                return true;
            }
        }else{
            return true;
        }

    }


    private void locateInit(){
        if(getLocate()!=null){
            try{
                WebDriver driver = this.getBrowser().getCurrentBrowserDriver();
                if(this.browser.isUseJQuery()){
                    if(getLocate().contains("->")){
                        By by = LocationParse.parseLocation(getLocate(),driver.getPageSource());
                        this.element=driver.findElement(by);
                        this.id= by.toString();
                    }else{
                        this.element = jquery(getLocate());
                        this.id=getLocate();
                    }
                }else{
                    this.element=driver.findElement(LocationParse.parseLocation(getLocate(),driver.getPageSource()));
                    setId(getLocate());
                }
            }catch (Exception e){
                logger.warn("元素{}不存在...",this.id);
                this.element=null;
            }

        }
    }

    public IBrowser getBrowser() {
        return browser;
    }


    public WebElement getElement(){
        return this.element;
    }



    @Override
    public IElement child(String location) {
        WebElement e=this.element.findElement(By.partialLinkText(location));
        CauliElement cauliElement = new CauliElement(getBrowser());
        cauliElement.setElement(e);
        return cauliElement;
    }

    @Override
    public IElement child(String location,int index) {
        WebElement e=this.element.findElements(By.partialLinkText(location)).get(index);
        CauliElement cauliElement = new CauliElement(getBrowser());
        cauliElement.setElement(e);
        return cauliElement;
    }

    @Override
    public CauliElements children(String location) {
        CauliElements cauliElements = new CauliElements(getBrowser(),location);
        cauliElements.setId(location);
        return cauliElements;
    }

    @Override
    public void contextClick() {
        ActionListenerProxy.getDispatcher().beforeContextClick();
        if(isExist()){
            this.actions.contextClick(this.element).build().perform();
        }else{
            logger.error("[" + id + "]元素查找失败，可能这个元素不存在，元素处contextClick失败！");
            throw new NoSuchElementException("["+id+"]元素查找失败，可能这个元素不存在，元素处contextClick失败！");
        }
        ActionListenerProxy.getDispatcher().afterContextClick();
    }



    public void setElement(WebElement element) {
		this.element = element;
	}
	
	public void keyPress(Keys key){
        ActionListenerProxy.getDispatcher().beforekeyPress();
        if(isExist()){
            this.element.sendKeys(key);
            logger.info("["+id+"]元素处按住左键");
        }else{
            logger.error("["+id+"]元素查找失败，可能这个元素不存在，元素处按住左键失败！");
            throw new NoSuchElementException("["+id+"]元素查找失败，可能这个元素不存在，元素处按住左键失败！");
        }
        ActionListenerProxy.getDispatcher().afterkeyPress();
	}
	
	public void release(){
        ActionListenerProxy.getDispatcher().beforeRelease();
        if(isExist()){
            this.actions.release(this.element).build().perform();
            logger.info("["+id+"]元素处松开鼠标或者按键");
        }else{
            logger.error("["+id+"]元素查找失败，可能这个元素不存在，鼠标或者按键失败！");
            throw new NoSuchElementException("["+id+"]元素查找失败，可能这个元素不存在，鼠标或者按键失败！");
        }
        ActionListenerProxy.getDispatcher().afterRelease();
	}

	@Override
	public void keyPress(String keys) {
        ActionListenerProxy.getDispatcher().beforekeyPress();
        if(isExist()){
            this.element.sendKeys(Keys.chord(keys));
            logger.info("["+id+"]元素处按住左键");
        }else{
            logger.error("["+id+"]元素查找失败，可能这个元素不存在，元素处按住左键失败！");
            throw new NoSuchElementException("["+id+"]元素查找失败，可能这个元素不存在，元素处按住左键失败！");
        }
        ActionListenerProxy.getDispatcher().afterkeyPress();
	}
}

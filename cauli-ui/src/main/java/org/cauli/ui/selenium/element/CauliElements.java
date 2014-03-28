package org.cauli.ui.selenium.element;

import org.apache.commons.io.IOUtils;
import org.cauli.ui.selenium.browser.IBrowser;
import org.openqa.selenium.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @auther sky
 */
public class CauliElements{

    private List<CauliElement> elements;
    private String id;
    private IBrowser browser;
    private String location;

    public CauliElements(IBrowser browser,String location){
        this.browser=browser;
        this.location=location;
        locateElements();

    }


    private void locateElements(){
        WebDriver driver = browser.getCurrentBrowserDriver();
        try{
            if(browser.isUseJQuery()){
                if(location.contains("->")){
                    By by = LocationParse.parseLocation(location,driver.getPageSource());
                    List<WebElement> webElements = driver.findElements(by);
                    this.elements=WebElementTransfer.transferWebElements(webElements,browser);
                }else{
                    List<WebElement> webElements = jquery(location,browser);
                    this.elements=WebElementTransfer.transferWebElements(webElements,browser);
                }
            }else{
                List<WebElement> webElements=driver.findElements(LocationParse.parseLocation(location,driver.getPageSource()));
                this.elements=WebElementTransfer.transferWebElements(webElements,browser);
            }
        }catch (Exception e){
            this.elements=null;
        }

    }


    protected List<WebElement> jquery(String jquery,IBrowser browser){
        InputStream inputStream=getClass().getClassLoader().getResourceAsStream("jquery.js");
        String jqueryJs = null;
        try {
            jqueryJs = IOUtils.toString(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        WebDriver driver = browser.getCurrentBrowserDriver();
        return (List<WebElement>) ( (JavascriptExecutor)driver).executeScript(jqueryJs+"return jQuery(\"" + jquery + "\")");
    }


    public CauliElement get(int index){
        if(isExist()){
            return this.elements.get(index);
        }else{
            throw new NoSuchElementException("没有找到CauliElements:"+this.location);
        }

    }

    public int size(){
        if(isExist()){
            return this.elements.size();
        }else{
            throw new NoSuchElementException("没有找到CauliElements:"+this.location);
        }
    }

    public List<CauliElement> getCauliElements(){
        if(isExist()){
            return this.elements;
        }else{
            throw new NoSuchElementException("没有找到CauliElements:"+this.location);
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public IBrowser getBrowser() {
        return browser;
    }

    public void setBrowser(IBrowser browser) {
        this.browser = browser;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isExist(){
        if(this.elements==null||this.elements.size()==0){
            locateElements();
            if(this.elements==null||this.elements.size()==0){
                return false;
            }else{
                return true;
            }
        }else{
            return true;
        }
    }
}

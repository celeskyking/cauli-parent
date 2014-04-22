package org.cauli.ui.selenium;


import org.cauli.ui.selenium.browser.*;
import org.cauli.ui.selenium.page.CurrentPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


/**
 * Created by tianqing.wang on 14-3-20
 */
public class DSL {
    public static ThreadLocal<Set<Engine>> browserSet=new ThreadLocal<Set<Engine>>(){
        public Set<Engine> initialValue(){
            return new HashSet<Engine>();
        }
    };
    private static Logger logger = LoggerFactory.getLogger(Auto.class);
    public  static ThreadLocal<BrowserManager> local = new ThreadLocal<BrowserManager>(){
        public BrowserManager initialValue(){
            return new BrowserManager();
        }
    };
    public static void require(Engine[] browsers){
        for(Engine browser:browsers){
            browserSet.get().add(browser);
        }
    }

    public static void require(String value){
        Engine b=Enum.valueOf(Engine.class,value.toUpperCase().trim());
        require(b);
    }

    public static void require(Engine browser){
        BrowserManager browserManager=new BrowserManager();
        browserManager.setBrowser(new Browser(browser));
        local.set(browserManager);

    }


    public static void require(Engine browser,String url){
        BrowserManager browserManager=new BrowserManager();
        try {
            browserManager.setBrowser(new Browser(browser,new URL(url)));
        } catch (MalformedURLException e) {
            logger.error("没有连接到远程节点的服务器，远程浏览器引用失败！请检查环境配置是否正确！");
            throw new RuntimeException("没有连接到远程节点的服务器，远程浏览器引用失败！请检查环境配置是否正确",e);
        }
        local.set(browserManager);
    }

    public static void requireRemote(Engine browser,String url){
        BrowserManager browserManager=new BrowserManager();
        try {
            browserManager.setBrowser(new RemoteBrowser(browser,new URL(url)));
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException("连接远程的serverURL出现了错误");
        }
        local.set(browserManager);
    }
    public static IBrowser browser(){
        return local.get().getBrowser();
    }
    private static CurrentPage currentPage;
    public static CurrentPage to(CurrentPage page){
          currentPage= page;
        return currentPage;
    }

    public static Object exec(String methodDesc,Objects...objectses) throws InvocationTargetException, IllegalAccessException {
        return currentPage.exec(methodDesc,objectses);
    }

    public <T extends CurrentPage>T page(Class<T> clazz){
        try {
            @SuppressWarnings("rawtypes")
            Constructor constructor=clazz.getConstructor(IBrowser.class);
            return (T) constructor.newInstance(browser());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

//    public Frame frame(int index){
//        return browser().currentPage().frame(index);
//    }


}

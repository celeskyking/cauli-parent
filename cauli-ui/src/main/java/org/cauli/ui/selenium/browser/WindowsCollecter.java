package org.cauli.ui.selenium.browser;


import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: ouamaqing
 * Date: 13-5-30
 * Time: 下午3:37
 * To change this template use File | Settings | File Templates.
 */
public class WindowsCollecter extends EventObject {
    private Logger logger = LoggerFactory.getLogger(WindowsCollecter.class);
    public List<String> windowhandles;
    public int windowNums;
    private IBrowser browser;
    public WindowsCollecter(Object source,IBrowser browser) {
        super(source);
        this.browser=browser;
        this.windowNums=0;
        this.windowhandles= Lists.newArrayList();

    }

    public void updateWindows(){
        if(browser.getWindows().size()-this.windowNums>1){
            logger.warn("在测试过程中出现了同时打开多个页面的情况，页面收集器将对页面的索引查找功能可能会出现混乱，谨慎使用通过index来切换页面。");
        }
        Set<String> handles=browser.getWindows();
        if(handles.size()>this.windowNums){
            logger.info("窗口的句柄数增多，进行句柄收集操作, 初始化Handles Number->{}",windowNums);
            if(!isAlertHandle()){
                for(String windowhandle:handles){
                    if(this.windowhandles.contains(windowhandle)){
                        continue;
                    }else{
                        this.windowNums=handles.size();
                        this.windowhandles.add(0,windowhandle);
                        logger.info("添加了新的窗口信息->"+windowhandle);
                    }
                }
            }
        }else if(handles.size()<this.windowNums){
            for(String windowhandle:this.windowhandles){
                if(handles.contains(windowhandle)){
                    continue;
                }else{
                    this.windowhandles.remove(windowhandle);
                    this.windowNums=handles.size();
                }
            }
        }

    }

    private boolean isAlertHandle(){
        try{
            browser.getCurrentBrowserDriver().switchTo().alert();
            return true;
        }catch (Exception e){
            return false;
        }

    }

    public String getLastWindowhandle(){
        return this.windowhandles.get(0);
    }

    public String getWindowhandleByIndex(int index){
        return this.windowhandles.get(index);
    }




}

package org.cauli.ui.selenium.element;

import jodd.util.StringUtil;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.nodes.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by celeskyking on 14-3-1
 */
public class LocationParse {

    private static Logger logger = LoggerFactory.getLogger(LocationParse.class);

    public static By parseLocation(String text,String pageSource){
        if(null==text||"".equals(text)){
            return null;
        }else{
            if(text.contains("->")){
                String strs[] = StringUtil.split(text, "->");
                if(strs.length<=1){
                    throw new RuntimeException("{}-> 定位方式写的不正确");
                }else{
                    return getLocationType(strs[0],strs[1]);
                }
            }else{
                try{
                    Document document = Jsoup.parse(pageSource);
                    Element element = document.select(text).first();
                    if(element==null){
                        return null;
                    }else{
                        JSoupElement jSoupElement = new JSoupElement(element);
                        return By.xpath(jSoupElement.toXpath());
                    }
                }catch (Exception e){
                    logger.error("请求的元素格式错误..请检查格式...",e);
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private static By getLocationType(String by,String location){
        try {
            By local = (By) MethodUtils.invokeStaticMethod(By.class, by, location);
            return local;
        } catch (Exception e) {
            logger.info("没有找到{}的定位方式,请检查定位方式书写是否正确...",by);
            e.printStackTrace();
            throw new NoSuchElementException("没有找到{}的定位方式,请检查定位方式书写是否正确...");
        }
    }


}

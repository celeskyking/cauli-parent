package org.cauli.mock.util;

import com.google.common.collect.Maps;
import org.dom4j.*;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by tianqing.wang on 2014/9/17
 */
public class XMLUtil {


    public static String getStringValue(Document document,String xpath){
        return document.selectSingleNode(xpath).getText();
    }

    public static int getIntValue(Document document,String xpath){
        String text =  document.selectSingleNode(xpath).getText();
        return Integer.valueOf(text);
    }

    public static double getDoubleValue(Document document,String xpath){
        String text =  document.selectSingleNode(xpath).getText();
        return Double.valueOf(text);
    }

    public static boolean getBooleanValue(Document document,String xpath){
        String text =  document.selectSingleNode(xpath).getText();
        return Boolean.valueOf(text);
    }

    public static long getLongValue(Document document,String xpath){
        String text =  document.selectSingleNode(xpath).getText();
        return Long.valueOf(text);
    }

    public static BigDecimal getBigDecimalValue(Document document, String xpath){
        String text =  document.selectSingleNode(xpath).getText();
        return new BigDecimal(text);
    }

    public static String getElementName(Document document,String xpath){
        return document.selectSingleNode(xpath).getName();
    }

    public static List<Element> getElements(Document document,String xpath){
        return document.selectNodes(xpath);
    }


    public static Map<String,String> getAttrs(Document document,String xpath){
        Map<String,String> map = Maps.newHashMap();
        Element element = (Element) document.selectSingleNode(xpath);
        Iterator<Attribute> iterator=element.attributeIterator();
        while(iterator.hasNext()){
            Attribute attribute  = iterator.next();
            map.put(attribute.getName(),attribute.getValue());
        }
        return map;
    }

    public static List<Element> getChildElements(Document document,String xpath){
        Element element = (Element) document.selectSingleNode(xpath);
        return element.elements();
    }


    public static String getElementName(Element element){
        return element.getName();
    }

    public static String getElementValue(Element element){
        return element.getText();
    }

    public static String getElementTrimValue(Element element){
        return element.getTextTrim();
    }

    public static Element getElement(Document document,String xpath){
        return (Element) document.selectSingleNode(xpath);
    }

    public static Element getChild(Element element,String childName){
        return element.element(childName);
    }

    public static Element getChild(Document document,String xpath,String childName){
        Element element = (Element) document.selectSingleNode(xpath);
        if(element==null){
            return null;
        }
        return element.element(childName);
    }

    public static List<Element> getChildren(Element element,String childName){
        return element.elements(childName);
    }

    public static List<Element> getChildren(Document document,String xpath,String childName){
        Element element = (Element) document.selectSingleNode(xpath);
        if(element==null){
            return null;
        }
        return element.elements(childName);
    }


    public static Element getParent(Element element){
        return element.getParent();
    }

    public static Element getParent(Document document,String xpath){
        return document.selectSingleNode(xpath).getParent();
    }


    public static Document load(String text){
        if(isXML(text)){
            try {
                return DocumentHelper.parseText(text);
            } catch (DocumentException e) {
                throw new RuntimeException(e);
            }
        }else{
            return null;
        }
    }

    public static boolean isXML(String text){
        try {
            DocumentHelper.parseText(text);
            return true;
        } catch (DocumentException e) {
            return false;
        }
    }

    public Element getRoot(Document docuemnt){
        return docuemnt.getRootElement();
    }


}

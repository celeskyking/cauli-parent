package org.cauli.ui.selenium.element;

import com.google.common.collect.Lists;
import org.cauli.ui.selenium.browser.IBrowser;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * @auther sky
 */
public class WebElementTransfer {

    public static List<CauliElement> transferWebElements(List<WebElement> webElements,IBrowser browser){
        List<CauliElement> cauliElements = Lists.newArrayList();
        for(WebElement webElement:webElements){
            cauliElements.add(transferWebElement(webElement,browser));
        }
        return cauliElements;
    }

    public static CauliElement transferWebElement(WebElement webElement,IBrowser browser){
        CauliElement cauliElement=new CauliElement(browser);
        cauliElement.setElement(webElement);
        return cauliElement;
    }
}

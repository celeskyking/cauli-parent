package org.cauli.ui.selenium.element;


import org.cauli.ui.selenium.browser.IBrowser;

public class Link extends CauliElement {


    public Link(IBrowser browser, TempElement tempElement) {
        super(browser, tempElement);
    }

    public Link(IBrowser browser) {
        super(browser);
    }

    public Link(IBrowser browser, String location) {
        super(browser, location);
    }

    public String getHref(){
        return this.getAttribute("href");
    }
}

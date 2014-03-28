package org.cauli.ui.selenium.element;


import org.cauli.ui.selenium.browser.IBrowser;

public class Image extends CauliElement {


    public Image(IBrowser browser, TempElement tempElement) {
        super(browser, tempElement);
    }

    public Image(IBrowser browser) {
        super(browser);
    }

    public Image(IBrowser browser, String location) {
        super(browser, location);
    }
}

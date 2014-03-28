package org.cauli.ui.selenium.element;


import org.cauli.ui.selenium.browser.IBrowser;

public class Button extends CauliElement {


    public Button(IBrowser browser, TempElement tempElement) {
        super(browser, tempElement);
    }

    public Button(IBrowser browser) {
        super(browser);
    }

    public Button(IBrowser browser, String location) {
        super(browser, location);
    }
}


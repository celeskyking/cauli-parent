package org.cauli.ui.selenium.page;

import org.cauli.ui.selenium.browser.IBrowser;

/**
 * Created by celeskyking on 14-3-2
 */
public class Frame extends CurrentPage{
    public Frame(IBrowser browser){
        super(browser);
    }

    private String location;

    private String index;


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}

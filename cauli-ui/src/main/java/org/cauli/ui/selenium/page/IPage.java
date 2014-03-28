package org.cauli.ui.selenium.page;

import org.openqa.selenium.Cookie;

import java.util.Set;

/**
 * @author celeskyking
 */
public interface IPage {

    public Set<Cookie> getAllCookies();

    void deleteAllCookies();

    public String getTitle();

    public String getUrl();

    public String getCookieByName(String name);


}

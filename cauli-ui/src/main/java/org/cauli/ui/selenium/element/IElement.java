package org.cauli.ui.selenium.element;

import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

/**
 * 元素接口
 */
public interface IElement {
    public IElement find(String location);
    public <T>T find(String location, Class<T> tClass);
    public void click();
    public void doubleClick();
    public void keyDown(Keys key);
    public void keyUp(Keys key);
    public void assertAttribute(String attr, String value);
    public void assertEditable();
    public void assertNotEditable();
    public void assertSelected();
    public void assertIsExist();
    public void assertText(String text);
    public void assertValue(String value);
    public IElement clear();
    public void input(String text);
    public void focus();
    public String getAttribute(String attr);
    public String getText();
    public String getCssValue(String name);
    public String getId();
    public void setId(String id);
    public Point getLocation();
    public int[] getSize();
    public String getTagName();
    public void scroll();
    public void mouseOver();
    public void submit();
    public boolean isExist();
    public boolean isDisplay();
    public boolean isEnable();
    public boolean isSelected();
    public void dragAndDrop(IElement element);
    public void dragAndDrop(Point point);
    public void leftDown();
    public void leftUp();
    public void keyPress(Keys key);
    public void keyPress(String keys);
    public void release();
    public WebElement getElement();
    public IElement child(String location);
    public IElement child(String location, int index);
    public CauliElements children(String location);
    public void contextClick();

    public String getLocate();
}

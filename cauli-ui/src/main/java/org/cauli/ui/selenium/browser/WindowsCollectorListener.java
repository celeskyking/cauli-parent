package org.cauli.ui.selenium.browser;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: ouamaqing
 * Date: 13-5-30
 * Time: 下午5:46
 * To change this template use File | Settings | File Templates.
 */
public class WindowsCollectorListener implements WindowsListener {
    @Override
    public void windowsCollecter(WindowsCollecter windowsCollecter) {
        windowsCollecter.updateWindows();

    }
}

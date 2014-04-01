package org.cauli.ui.selenium.browser;

import java.util.Enumeration;
import java.util.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: ouamaqing
 * Date: 13-5-30
 * Time: 下午3:45
 * To change this template use File | Settings | File Templates.
 */
public class WindowSource {



    private Vector repository =new Vector();
    private WindowsListener windowsListener;

    public WindowsCollecter getWindowsCollecter() {
        return windowsCollecter;
    }

    private WindowsCollecter windowsCollecter;
    private IBrowser browser;
    public void addWindowsListener(WindowsListener windowsListener){
        this.repository.add(windowsListener);
    }

    public void notifyWindowsCollecter(WindowsCollecter windowsCollecter){
        Enumeration enumeration=repository.elements();
        while(enumeration.hasMoreElements())
        {
            windowsListener = (WindowsListener)enumeration.nextElement();
            windowsListener.windowsCollecter(windowsCollecter);
        }
    }

    public void removeWindowListener(WindowsListener windowsListener){
        this.repository.remove(windowsListener);
    }

    public void windowsCheck(){
//        if(windowsCollecter.windowNums==browser.getWindows().size()){
//             notifyWindowsCollecter(windowsCollecter);
//        }
        notifyWindowsCollecter(windowsCollecter);
    }


    public WindowSource(IBrowser browser){
        this.browser=browser;
        this.windowsCollecter=new WindowsCollecter(this,browser);
    }


}

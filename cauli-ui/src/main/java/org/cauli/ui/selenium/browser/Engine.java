package org.cauli.ui.selenium.browser;

import com.opera.core.systems.OperaDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.net.URL;


/**

 */
public enum Engine{
    PHANTOMJS(){
        @Override
        protected PhantomJSDriver browser() {
            return new PhantomJSDriver(new DesiredCapabilities());
        }

        @Override
        protected RemoteWebDriver browser(URL url) {
            return new RemoteWebDriver(url, DesiredCapabilities.phantomjs());
        }

    },
    IE(){
        public InternetExplorerDriver browser(){
            return new InternetExplorerDriver();
        }
        public RemoteWebDriver browser(URL url){
            return new RemoteWebDriver(url, DesiredCapabilities.internetExplorer());
        }
        
    },
    FIREFOX(){
        public FirefoxDriver browser(){
              return new FirefoxDriver();
          }
        public RemoteWebDriver browser(URL url){
            return new RemoteWebDriver(url, DesiredCapabilities.firefox());
        }
       
    },
    CHROME(){
        public ChromeDriver browser(){
                  return new ChromeDriver();
        }
        public RemoteWebDriver browser(URL url){
            return new RemoteWebDriver(url, DesiredCapabilities.chrome());
        }
       
    },
    SAFARI(){
        public SafariDriver browser(){
            return new SafariDriver();
        }
        public RemoteWebDriver browser(URL url){
            return new RemoteWebDriver(url, DesiredCapabilities.safari());
        }
        
    },
    OPERA(){
        public OperaDriver browser(){
            return new OperaDriver();
        }
        public RemoteWebDriver browser(URL url){
            return new RemoteWebDriver(url, DesiredCapabilities.opera());
        }
    };

    protected abstract <T> T browser();

    protected abstract RemoteWebDriver browser(URL url);

}

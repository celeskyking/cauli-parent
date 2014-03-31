package org.cauli.ui.runner;

import jodd.util.StringUtil;
import org.cauli.junit.statement.InterceptorStatement;
import org.cauli.ui.annotation.Require;
import org.cauli.ui.selenium.browser.Auto;
import org.cauli.ui.selenium.browser.Engine;
import org.junit.runners.model.FrameworkMethod;

/**
 * Created by celeskyking on 14-3-1
 */
public class CauliUIStatment extends InterceptorStatement{

    public CauliUIStatment(FrameworkMethod testMethod, Object target) {
        super(testMethod, target);
    }




    @Override
    public void evaluate() throws Throwable {
        String browsers = CauliConfigUtil.getInstance().get("browsers");
        if(browsers!=null){
            String[] strings = StringUtil.split(browsers,"|");
            for(String browser:strings){
                System.out.println(browser);
                Auto.require(browser);
                super.evaluate();
                if(!Auto.browser().isClosed()){
                    Auto.browser().closeAllWindows();
                }
            }
        }else{
            FrameworkMethod frameworkMethod = getTestMethod();
            if(frameworkMethod.getMethod().isAnnotationPresent(Require.class)){
                Engine engine = frameworkMethod.getAnnotation(Require.class).value();
                String url = frameworkMethod.getAnnotation(Require.class).url();
                if(null==url||"".equals(url)){
                    Auto.require(engine);
                }else{
                    Auto.require(engine,url);
                }
            }else if(frameworkMethod.getMethod().getDeclaringClass().isAnnotationPresent(Require.class)){
                Engine engine = frameworkMethod.getMethod().getDeclaringClass().getAnnotation(Require.class).value();
                String url = frameworkMethod.getMethod().getDeclaringClass().getAnnotation(Require.class).url();
                if(null==url||"".equals(url)){
                    Auto.require(engine);
                }else{
                    Auto.require(engine,url);
                }
            }
            super.evaluate();
        }


    }
}

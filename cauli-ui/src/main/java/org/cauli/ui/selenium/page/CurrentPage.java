package org.cauli.ui.selenium.page;

import com.google.common.collect.Maps;
import org.cauli.ui.annotation.Description;
import org.cauli.ui.selenium.browser.IBrowser;
import org.cauli.ui.selenium.element.CauliElements;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.lang.reflect.Method;

/**
 * Created by tianqing.wang on 14-3-17
 */
public class CurrentPage extends Page{

    private Map<String,Method> dslMethods ;

    public CurrentPage(IBrowser browser){
        super(browser);
        this.dslMethods= Maps.newHashMap();
        Method[] methods = this.getClass().getMethods();
        for(Method method:methods){
            if(method.isAnnotationPresent(Description.class)){
                this.dslMethods.put(method.getAnnotation(Description.class).value(),method);
                this.dslMethods.put(method.getName(),method);
            }
        }

    }

    public Object exec(String methodDesc,Object...objects) throws InvocationTargetException, IllegalAccessException {
        Method method = this.dslMethods.get(methodDesc);
        return method.invoke(this,objects);

    }


}

package org.cauli.ui.selenium.page;


import org.cauli.ui.annotation.At;
import org.cauli.ui.annotation.Commit;
import org.cauli.ui.annotation.Find;
import org.cauli.ui.annotation.Title;
import org.cauli.ui.selenium.browser.Auto;
import org.cauli.ui.selenium.browser.IBrowser;
import org.cauli.ui.selenium.element.CauliElement;
import org.cauli.ui.selenium.element.CauliElements;
import org.cauli.ui.selenium.element.IElement;
import org.cauli.ui.selenium.element.TempElement;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

/**
 * @author 
 */
public abstract class SourcePage extends CurrentPage {

    private String pageCommit;
    private String configUrl;
    private String configTitle;
    public SourcePage(IBrowser browser){
        super(browser);
        setBrowser(browser);

        if(this.getClass().isAnnotationPresent(Commit.class)){
            this.pageCommit=this.getClass().getAnnotation(Commit.class).value();
        }
        if(this.getClass().isAnnotationPresent(At.class)){
            if(!browser.getCurrentBrowserDriver().getCurrentUrl().contains(this.getClass().getAnnotation(At.class).value())){
                this.configUrl=this.getClass().getAnnotation(At.class).value();
                browser.selectWindowContainsUrl(this.getClass().getAnnotation(At.class).value());
            }
        }else if(this.getClass().isAnnotationPresent(Title.class)){
            if(!browser.getCurrentBrowserDriver().getCurrentUrl().contains(this.getClass().getAnnotation(Title.class).value())){
                this.configTitle=this.getClass().getAnnotation(Title.class).value();
                browser.selectWindowContainsTitle(this.getClass().getAnnotation(Title.class).value());
            }
        }
        init();
    }

    public String getConfigUrl() {
        return configUrl;
    }

    public void setConfigUrl(String configUrl) {
        this.configUrl = configUrl;
    }

    public String getConfigTitle() {
        return configTitle;
    }

    public void setConfigTitle(String configTitle) {
        this.configTitle = configTitle;
    }

    public void setPageCommit(String pageCommit) {
        this.pageCommit = pageCommit;
    }

    public String getPageCommit() {
        return pageCommit;
    }

    private void init(){
    	Field[] fields = this.getClass().getDeclaredFields();
    	for(Field field:fields){
    		field.setAccessible(true);
    		if(field.isAnnotationPresent(Find.class)){
                if((field.getType()== TempElement.class)){
                    Find find = field.getAnnotation(Find.class);
                    String id = find.id();
                    if("".equals(id)){
                        throw new RuntimeException("页面定义的tempElement必须有id值，否则无法通过id查找元素的...");
                    }
                    if(!"".equals(find.value())){
                        String value = find.value();
                        TempElement tempElement = new TempElement(id,value);
                        try {
                            field.set(this, tempElement);
                            addElement(find.id(),(TempElement) field.get(this));
                        } catch (IllegalArgumentException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }else if(IElement.class.isAssignableFrom(field.getType())){
                    Find find = field.getAnnotation(Find.class);
                    String id = find.id();
                    if(!"".equals(find.value())){
                        String value = find.value();
                        try {
                            Constructor constructor = field.getType().getConstructor(IBrowser.class);
                            CauliElement cauliElement = (CauliElement) constructor.newInstance(getBrowser());
                            cauliElement.setLocate(value);
                            if("".equals(id)){
                                cauliElement.setId(value);
                            }else{
                                cauliElement.setId(id);
                            }
                            field.set(this, cauliElement);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }else if(field.getType()== CauliElements.class){
                    Find find = field.getAnnotation(Find.class);
                    String id = find.id();
                    if(!"".equals(find.value())){
                        String location = find.value();
                        CauliElements cauliElements = new CauliElements(getBrowser(),location);
                        try {
                            if("".equals(id)){
                                cauliElements.setId(location);
                            }else{
                                cauliElements.setId(id);
                                addCauliElements(cauliElements);
                            }
                            field.set(this, cauliElements);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }else{
                    throw new RuntimeException("属性类型错误,定义的属性只能够为临时元素,为TempElement类型:"+field.getName());
                }
            }
    	}

    }

}

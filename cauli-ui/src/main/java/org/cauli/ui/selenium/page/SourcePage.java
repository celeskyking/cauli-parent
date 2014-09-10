package org.cauli.ui.selenium.page;

import com.thoughtworks.xstream.XStream;
import jodd.util.StringUtil;
import org.cauli.ui.annotation.Commit;
import org.cauli.ui.annotation.Find;
import org.cauli.ui.annotation.Source;
import org.cauli.ui.selenium.LocateSource;
import org.cauli.ui.selenium.browser.IBrowser;
import org.cauli.ui.selenium.element.CauliElement;
import org.cauli.ui.selenium.element.CauliElements;
import org.cauli.ui.selenium.element.IElement;
import org.cauli.ui.source.ElementEntity;
import org.cauli.ui.source.PageEntity;
import org.cauli.ui.source.PagesEntity;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;

/**
 * @author 
 */
public abstract class SourcePage extends CurrentPage implements LocateSource{

    private String pageCommit;
    private String configUrl;
    private String configTitle;



    public SourcePage(IBrowser browser) throws Exception {
        super(browser);
        setBrowser(browser);
        if(this.getClass().isAnnotationPresent(Commit.class)){
            Commit commit = getClass().getAnnotation(Commit.class);
            this.pageCommit=commit.value();
            this.configTitle=commit.title();
            this.configUrl=commit.url();
            if(!StringUtil.isEmpty(configTitle)){
                getBrowser().selectWindowContainsTitle(configTitle);
            }else{
                getBrowser().selectWindowContainsUrl(configUrl);
            }
        }else{
            throw new Exception(getClass().getSimpleName()+"必须定义Commimt注解信息");
        }
        init();
        initFrameAndSubPage();
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

    private void init() throws Exception {
    	Field[] fields = this.getClass().getDeclaredFields();
    	for(Field field:fields){
    		field.setAccessible(true);
    		if(field.isAnnotationPresent(Find.class)){
                if(IElement.class.isAssignableFrom(field.getType())){
                    parseCauliElement(field);
                }else if(field.getType()== CauliElements.class){
                    parseCauliElements(field);
                }else{
                    throw new RuntimeException("属性类型错误,定义的属性只能够为临时元素,为TempElement类型:"+field.getName());
                }
            }
    	}
        load();
    }

    /**解析cauli元素*/
    private void parseCauliElement(Field field){
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
                addCauliElement(cauliElement);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**解析cauli*/
    private void parseCauliElements(Field field){
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
    }


    @Override
    public void load() throws Exception {
        if(getClass().isAnnotationPresent(Source.class)){
            String fileName = getClass().getAnnotation(Source.class).value();
            File file = ResourceUtils.getFile(fileName);
            XStream xStream = new XStream();
            xStream.processAnnotations(new Class[]{PagesEntity.class,PageEntity.class, ElementEntity.class});
            PagesEntity pages = (PagesEntity) xStream.fromXML(file);
            for(PageEntity pageEntity:pages.getPages()){
                if(pageEntity.getName().equals(this.pageCommit)){
                    List<ElementEntity> elementEntities = pageEntity.getElements();
                    for(ElementEntity elementEntity:elementEntities){
                        CauliElement cauliElement = new CauliElement(getBrowser(),elementEntity.getLocate());
                        cauliElement.setId(elementEntity.getId());
                        addCauliElement(cauliElement);
                    }
                }
            }
        }
    }
}

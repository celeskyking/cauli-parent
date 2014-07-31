package org.cauli.ui.selenium;

import com.google.common.collect.Maps;
import org.cauli.instrument.ClassPool;
import org.cauli.instrument.ClassUtils;
import org.cauli.ui.annotation.Commit;
import org.cauli.ui.selenium.page.Frame;
import org.cauli.ui.selenium.page.SourcePage;
import org.cauli.ui.selenium.page.SubPage;

import java.util.Map;
import java.util.Set;

/**
 * Created by tianqing.wang on 2014/7/25
 */
public class PageHelper {
    private Map<String,Class<? extends Frame>> frameMap = Maps.newHashMap();
    private Map<String,Class<? extends SubPage>> subPageMap = Maps.newHashMap();
    private Map<String,Class<? extends SourcePage>> sourcePageMap = Maps.newHashMap();

    private volatile static PageHelper pageHelper;

    private PageHelper(){
        Set<Class<?>> classes = ClassPool.getClassPool();
        for(Class<?> clazz : classes){
            if(ClassUtils.isAssignableFromSubClass(Frame.class,clazz)){
                frameMap.put(clazz.getAnnotation(Commit.class)==null?clazz.getSimpleName():clazz.getAnnotation(Commit.class).value(), (Class<? extends Frame>) clazz);
            }else if(ClassUtils.isAssignableFromSubClass(SubPage.class,clazz)){
                subPageMap.put(clazz.getAnnotation(Commit.class)==null?clazz.getSimpleName():clazz.getAnnotation(Commit.class).value(), (Class<? extends SubPage>) clazz);
            }else if(ClassUtils.isAssignableFromSubClass(Commit.class,clazz)){
                sourcePageMap.put(clazz.getAnnotation(Commit.class)==null?clazz.getSimpleName():clazz.getAnnotation(Commit.class).value(), (Class<? extends SourcePage>) clazz);
            }
        }
    }


    public static PageHelper getInstance(){
        if(pageHelper==null){
            synchronized (PageHelper.class){
                if(pageHelper==null){
                    pageHelper=new PageHelper();
                }
            }
        }
        return pageHelper;
    }


    public Map<String, Class<? extends Frame>> getFrameMap() {
        return frameMap;
    }

    public void setFrameMap(Map<String, Class<? extends Frame>> frameMap) {
        this.frameMap = frameMap;
    }

    public Map<String, Class<? extends SubPage>> getSubPageMap() {
        return subPageMap;
    }

    public void setSubPageMap(Map<String, Class<? extends SubPage>> subPageMap) {
        this.subPageMap = subPageMap;
    }

    public Map<String, Class<? extends SourcePage>> getSourcePageMap() {
        return sourcePageMap;
    }

    public void setSourcePageMap(Map<String, Class<? extends SourcePage>> sourcePageMap) {
        this.sourcePageMap = sourcePageMap;
    }
}

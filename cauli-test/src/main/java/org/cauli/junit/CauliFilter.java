package org.cauli.junit;

import jodd.util.StringUtil;
import org.cauli.junit.anno.Tag;
import org.junit.runner.Description;
import org.junit.runner.manipulation.Filter;

/**
 * Created by celeskyking on 14-3-1
 */
public class CauliFilter extends Filter{
    private String filterContent="";
    @Override
    public boolean shouldRun(Description description) {
        if(!description.getTestClass().isAnnotationPresent(org.cauli.junit.anno.Filter.class)){
            if(null!=filterContent&&!getFilterContent().equals("")){
                return isMatch(getFilterContent(),description);
            }
            return true;
        }else{
            org.cauli.junit.anno.Filter filter = description.getTestClass().getAnnotation(org.cauli.junit.anno.Filter.class);
            return isMatch(filter.feature()+"."+filter.release(),description);
        }
    }

    @Override
    public String describe() {
        return "all tests";
    }

    protected boolean isMatch(String text,Description description){
        String filterFeature = StringUtil.split(text,".")[0];
        String filterRelease = "";
        if(text.contains(".")){
            filterRelease = StringUtil.split(text,".")[1];
        }
        org.cauli.junit.anno.Tag tag = description.getAnnotation(Tag.class);
        if(null==tag){
            return false;
        }
        if("".equals(filterRelease)){
            if (filterFeature.equals(tag.feature())){
                return true;
            }else{
                return false;
            }
        }else{
            if(filterFeature.equals(tag.feature())&&filterRelease.equals(tag.release())){
                return true;
            }else{
                return false;
            }
        }

    }

    public String getFilterContent() {
        return filterContent;
    }

    public void setFilterContent(String filter) {
        this.filterContent = filter;
    }
}

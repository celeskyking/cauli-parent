package org.cauli.junit;

import org.cauli.junit.anno.Tag;
import org.junit.runner.Description;
import org.junit.runner.manipulation.Filter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by celeskyking on 14-3-1
 */
public class CauliFilter extends Filter{
    @Override
    public boolean shouldRun(Description description) {
        if(!description.getTestClass().isAnnotationPresent(org.cauli.junit.anno.Filter.class)||
                !description.getTestClass().isAnnotationPresent(Tag.class)){
            return true;
        }else{
            String value = description.getTestClass().getAnnotation(org.cauli.junit.anno.Filter.class).value();
            String tagName = description.getAnnotation(Tag.class).value();
            Pattern pattern = Pattern.compile(value,Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(tagName);
            return matcher.find();
        }
    }

    @Override
    public String describe() {
        return "all tests";
    }

}

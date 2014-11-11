package org.cauli.common.instrument;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Set;

/**
 * Created by tianqing.wang on 2014/10/27
 */
public class ClassConfig {

    private List<ClassMatcher> conditions= Lists.newArrayList();

    private Set<Class<?>> classSet;

    public ClassConfig(String baseName){
        classSet = ClassPool.getClassPool(baseName);
    }

    private boolean isMatch(Class<?> each){
        for(ClassMatcher classMatch : conditions){
            if(!classMatch.match(each)){
                return false;
            }
        }
        return true;
    }

    public ClassConfig(){
        classSet= ClassPool.getClassPool();
    }

    public ClassConfig withAnnotation(final Class<? extends Annotation> cls){
        conditions.add(new ClassMatcher() {
            @Override
            public boolean match(Class<?> clazz) {
                if(clazz.isAnnotationPresent(cls)){
                    return true;
                }else{
                    return false;
                }
            }
        });
        return this;
    }

    public ClassConfig withInherit(final Class<?> clazz){
        conditions.add(new ClassMatcher() {
            @Override
            public boolean match(Class<?> cls) {
                if(ClassUtils.isAssignableFromSubClass(clazz, cls)){
                    return true;
                }else{
                    return false;
                }
            }
        });
        return this;
    }

    public ClassConfig isNotInterface(){
        conditions.add(new ClassMatcher() {
            @Override
            public boolean match(Class<?> cls) {
                if(cls.isInterface()){
                    return false;
                }else{
                    return true;
                }
            }
        });
        return this;
    }

    public ClassConfig isInterface(){
        conditions.add(new ClassMatcher() {
            @Override
            public boolean match(Class<?> cls) {
                if(cls.isInterface()){
                    return true;
                }else{
                    return false;
                }
            }
        });
        return this;
    }

    public ClassConfig isAbstract(){
        conditions.add(new ClassMatcher() {
            @Override
            public boolean match(Class<?> cls) {
                if(Modifier.isAbstract(cls.getModifiers())){
                    return true;
                }else{
                    return false;
                }
            }
        });
        return this;
    }

    public ClassConfig isNotAbstract(){
        conditions.add(new ClassMatcher() {
            @Override
            public boolean match(Class<?> cls) {
                if(Modifier.isAbstract(cls.getModifiers())){
                    return false;
                }else{
                    return true;
                }
            }
        });
        return this;
    }

    public ClassConfig named(final String className){
        conditions.add(new ClassMatcher() {
            @Override
            public boolean match(Class<?> cls) {
                if(cls.getName().equals(className)){
                    return true;
                }else{
                    return false;
                }
            }
        });
        return this;
    }

    public ClassConfig isFinal(){
        conditions.add(new ClassMatcher() {
            @Override
            public boolean match(Class<?> cls) {
                if(Modifier.isFinal(cls.getModifiers())){
                    return true;
                }else{
                    return false;
                }
            }
        });
        return this;
    }

    public Set<Class<?>> classes(){
        Set<Class<?>> classes = Sets.filter(classSet,new Predicate<Class<?>>() {
            @Override
            public boolean apply(Class<?> input) {
                return isMatch(input);
            }
        });
        return classes;
    }

}

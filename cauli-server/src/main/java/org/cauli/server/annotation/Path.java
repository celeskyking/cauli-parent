package org.cauli.server.annotation;

import org.cauli.server.HttpMethod;

import java.lang.annotation.*;

/**
 * Created by tianqing.wang on 2014/9/1
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
public @interface Path {
    String value();
    HttpMethod[] methods() default {HttpMethod.GET,HttpMethod.POST};

}

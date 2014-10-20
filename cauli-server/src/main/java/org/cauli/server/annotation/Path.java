package org.cauli.server.annotation;

import org.cauli.server.HttpMethod;

import java.lang.annotation.*;
import java.util.List;

/**
 * Created by tianqing.wang on 2014/9/1
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE,ElementType.FIELD})
@Inherited
public @interface Path {
    String value();
    String produce() default "text/plain; charset=UTF-8";
    HttpMethod[] methods() default {HttpMethod.GET,HttpMethod.POST};

}

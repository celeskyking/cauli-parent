package org.cauli.junit.anno;

import org.cauli.junit.RunnerInterceptor;

import java.lang.annotation.*;

/**
 * Created by tianqing.wang on 2014/6/20
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface Interceptor {
    Class<? extends RunnerInterceptor>[] value();
}

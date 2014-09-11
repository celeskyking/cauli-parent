package org.cauli.mock.annotation;

import java.lang.annotation.*;

/**
 * Created by tianqing.wang on 2014/8/13
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
public @interface Route {
}

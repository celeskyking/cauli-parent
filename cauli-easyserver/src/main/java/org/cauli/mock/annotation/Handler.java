package org.cauli.mock.annotation;

import java.lang.annotation.*;

/**
 * Created by tianqing.wang on 2014/8/21
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
public @interface Handler {
}

package org.cauli.junit.anno;

import java.lang.annotation.*;

/**
 * Created by tianqing.wang on 14-2-27
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
public @interface Param {
    String value() default "default";
    String type() default "row";
}


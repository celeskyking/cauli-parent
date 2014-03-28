package org.cauli.ui.annotation;

import java.lang.annotation.*;

/**
 * Created by tianqing.wang on 14-3-20
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface FindFrame {

    int index() default 0;
    String location() default "";
}

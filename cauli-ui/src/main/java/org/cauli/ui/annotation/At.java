package org.cauli.ui.annotation;

import java.lang.annotation.*;

/**
 * Created by celeskyking on 14-3-1
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface At {
    String value() default "";
}

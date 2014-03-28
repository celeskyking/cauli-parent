package org.cauli.ui.annotation;

import java.lang.annotation.*;

/**
 * Created by celeskyking on 14-3-1
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Inherited
public @interface Find {
    String id() default "";
    String value();
}

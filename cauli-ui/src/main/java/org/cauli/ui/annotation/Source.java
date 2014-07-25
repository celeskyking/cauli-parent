package org.cauli.ui.annotation;

import java.lang.annotation.*;

/**
 * Created by tianqing.wang on 2014/7/25.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Inherited
public @interface Source {
    String value();
}

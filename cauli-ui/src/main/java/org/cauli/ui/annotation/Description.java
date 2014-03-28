package org.cauli.ui.annotation;

import java.lang.annotation.*;

/**
 * Created by tianqing.wang on 14-3-1
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
public @interface Description {
    String value();
}

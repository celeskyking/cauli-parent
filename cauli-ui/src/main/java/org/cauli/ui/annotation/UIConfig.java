package org.cauli.ui.annotation;

import java.lang.annotation.*;

/**
 * Created by tianqing.wang on 2014/9/24
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
@Inherited
public @interface UIConfig {
}

package org.cauli.ui.annotation;

import org.cauli.ui.selenium.browser.Engine;

import java.lang.annotation.*;

/**
 * Created by celeskyking on 14-3-1
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
@Inherited
public @interface Require {
    String url() default "";
    Engine[] value();
}

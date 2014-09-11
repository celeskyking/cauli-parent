package org.cauli.mock.annotation;

import org.cauli.mock.ConfigType;

import java.lang.annotation.*;

/**
 * @auther sky
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
public @interface Action {
    ConfigType value();
}

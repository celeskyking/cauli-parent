package org.cauli.mock.annotation;

import org.cauli.mock.ValueType;

import java.lang.annotation.*;

/**
 * Created by tianqing.wang on 2014/8/7
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
public @interface Value {

    String value();
    ValueType type();

}

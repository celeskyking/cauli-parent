package org.cauli.db.annotation;


import java.lang.annotation.*;

/**
 * Created by tianqing.wang on 14-3-21
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface Mapper {
    Class<?> value();
}

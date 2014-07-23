package org.cauli.junit.anno;

import java.lang.annotation.*;

/**
 * Created by tianqing.wang on 2014/6/5
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
public @interface Dependency {
    String[] value();
}

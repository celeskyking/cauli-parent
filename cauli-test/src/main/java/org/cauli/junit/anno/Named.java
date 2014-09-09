package org.cauli.junit.anno;

import java.lang.annotation.*;

/**
 * Created by tianqing.wang on 2014/6/6
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Inherited
public @interface Named {
}

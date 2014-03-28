package org.cauli.junit.anno;

import java.lang.annotation.*;

/**
 * Created by tianqing.wang on 14-3-18
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Inherited
public @interface Bean {
    String value();
}

package org.cauli.junit.anno;

import java.lang.annotation.*;

/**
 * Created by tianqing.wang on 14-3-18
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface FileParse {
    String suffix();
}

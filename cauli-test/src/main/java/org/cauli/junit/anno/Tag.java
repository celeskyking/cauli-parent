package org.cauli.junit.anno;

import java.lang.annotation.*;

/**
 * Created by celeskyking on 14-3-1
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
public @interface Tag {
    String name();
    String feature() default "default";
    String release() default "";
    int level() default 2;
}

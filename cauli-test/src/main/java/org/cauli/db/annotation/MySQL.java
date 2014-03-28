package org.cauli.db.annotation;

import java.lang.annotation.*;

/**
 * Created by tianqing.wang on 14-3-20
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
@Inherited
public @interface MySQL {
    String username();
    String password();
    String url();
    String id();
}

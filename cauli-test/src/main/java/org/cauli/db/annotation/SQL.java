package org.cauli.db.annotation;

import java.lang.annotation.*;

/**
 * Created by tianqing.wang on 14-3-20
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
public @interface SQL {
    String value();
    //数据库的名字
    String id();
}

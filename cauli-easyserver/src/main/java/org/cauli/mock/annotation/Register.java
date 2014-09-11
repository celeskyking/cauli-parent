package org.cauli.mock.annotation;

import java.lang.annotation.*;

/**
 * Created by tianqing.wang on 2014/8/7
 *
 * 这个注解用来实现注册Freemarker方法的功能
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface Register {


}

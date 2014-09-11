package org.cauli.mock.annotation;

import java.lang.annotation.*;

/**
 * Created by tianqing.wang on 2014/8/14
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
@Inherited
public @interface SocketRequest {
}

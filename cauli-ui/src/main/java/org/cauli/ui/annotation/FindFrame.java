package org.cauli.ui.annotation;

import org.cauli.ui.FrameLocateType;

import java.lang.annotation.*;

/**
 * Created by tianqing.wang on 14-3-20
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface FindFrame {

    String value();
    FrameLocateType type();
}

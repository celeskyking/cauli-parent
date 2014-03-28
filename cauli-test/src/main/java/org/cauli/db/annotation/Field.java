package org.cauli.db.annotation;


import org.springframework.jdbc.core.RowMapper;

import java.lang.annotation.*;

/**
 * Created by tianqing.wang on 14-3-20
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Field {
    String value();
    Class<? extends RowMapper> mapper() default RowMapper.class;
}

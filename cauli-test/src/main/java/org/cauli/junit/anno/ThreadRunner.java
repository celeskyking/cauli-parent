package org.cauli.junit.anno;


import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ThreadRunner {
	int threads() default 1;
}

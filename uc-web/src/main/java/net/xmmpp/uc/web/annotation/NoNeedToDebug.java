package net.xmmpp.uc.manager.web.annotation;

import java.lang.annotation.*;


@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NoNeedToDebug {

    boolean debug() default false;
}

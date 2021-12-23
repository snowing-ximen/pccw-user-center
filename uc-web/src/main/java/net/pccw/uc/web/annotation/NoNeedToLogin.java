package net.pccw.uc.manager.web.annotation;

import java.lang.annotation.*;


@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NoNeedToLogin {

}

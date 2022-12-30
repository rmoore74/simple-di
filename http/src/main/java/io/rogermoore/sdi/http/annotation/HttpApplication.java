package io.rogermoore.sdi.http.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface HttpApplication {
    String value() default "";
}

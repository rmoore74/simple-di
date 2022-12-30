package io.rogermoore.sdi.http.annotation.method;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Get {
    String value() default "";
}

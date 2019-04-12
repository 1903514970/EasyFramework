package com.dj.frameworklib.utils.shared_preference;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by dengjun on 2019/4/8.
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SPReturnType {
    SPReturnTypeEnum returnType();

    String defaultValue() default "";
}

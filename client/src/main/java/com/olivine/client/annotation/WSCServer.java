package com.olivine.client.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description: 远程连接地址
 * @author: jphao
 * @date: 2021/8/25 14:24
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface WSCServer {

    String value() default "";
}

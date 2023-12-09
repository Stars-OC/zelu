package com.ssgroup.zelu.aop.permission;

import com.ssgroup.zelu.pojo.type.Role;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Clusters_stars
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
public @interface Permission {
    Role[] value() default {Role.USER};
}

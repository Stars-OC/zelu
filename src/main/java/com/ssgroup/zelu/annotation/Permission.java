package com.ssgroup.zelu.annotation;

import com.ssgroup.zelu.pojo.type.Role;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 对于权限的相关注解
 *
 * @apiNote    value 角色 Role[] 枚举类型的数组
 * isIndividual  是否是个体
 * (类注解下将此设为true将不受全类角色的影响)
 *
 * @author Clusters_stars
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
public @interface Permission {
    Role[] value() default {Role.USER};

    boolean isIndividual() default false;
}

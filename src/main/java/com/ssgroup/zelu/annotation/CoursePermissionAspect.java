package com.ssgroup.zelu.annotation;

import com.ssgroup.zelu.pojo.request.SchoolAndCourseId;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(2)
public class CoursePermissionAspect {

    @Pointcut("@annotation(com.ssgroup.zelu.annotation.CoursePermission)")
    private void annotationPointCut() {
    }

    @Pointcut("args(com.ssgroup.zelu.pojo.request.SchoolAndCourseId,..) && args(request,..)")
    private void argsPointCut(SchoolAndCourseId request){
    }

    @Around("annotationPointCut() && argsPointCut(request)")
    public Object annotationArgsCheck(ProceedingJoinPoint point, SchoolAndCourseId request) throws Throwable {
        System.out.println(request);
        return point.proceed();
    }
}

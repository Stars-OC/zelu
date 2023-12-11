package com.ssgroup.zelu.aop;

import com.ssgroup.zelu.pojo.user.User;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;

//@Aspect
@Component
@Order(5)
public class RequestUserAspect {

    @Pointcut("@annotation(com.ssgroup.zelu.aop.RequestUser)")
    private void requestUserPointCut() {
    }

    /**
     * 该注解用于在调用方法时，检查并解析用户信息。
     * 切面表达式：requestUserPointCut() && args(user)
     *
     * @param joinPoint 方法调用的 ProceedingJoinPoint 对象
     * @param user 用户对象
     * @return 方法调用的返回值
     * @throws Throwable 可能抛出的异常
     */
    @Around("requestUserPointCut() && args(user,..)")
    public Object parseUser(ProceedingJoinPoint joinPoint, User user) throws Throwable {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RequestUser requestUser = method.getAnnotation(RequestUser.class);
        String value = requestUser.value();

        if ("token".equals(value)) {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            String token = request.getHeader("token");
            user = new User(token);
            Object[] args = joinPoint.getArgs();
            args[0] = user;
            return joinPoint.proceed(args);
        }

        return null;
    }

}

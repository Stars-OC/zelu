package com.ssgroup.zelu.aop.permission;

import com.ssgroup.zelu.pojo.Result;
import com.ssgroup.zelu.pojo.type.ResultCode;
import com.ssgroup.zelu.pojo.type.Role;
import com.ssgroup.zelu.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;

@Aspect
@Component
public class PermissionAspect {

    @Pointcut("@annotation(com.ssgroup.zelu.aop.permission.Permission)")
    private void permissionPointCut() {
    }

    /**
     * 检查权限切面方法
     *
     * @param point 函数调用的 ProceedingJoinPoint 对象
     * @return 方法的返回值
     * @throws Throwable 异常信息
     */
    @Around("permissionPointCut()")
    public Object checkPermission(ProceedingJoinPoint point) throws Throwable {
        // 获取方法签名
        MethodSignature signature = (MethodSignature) point.getSignature();
        // 获取方法对象
        Method method = signature.getMethod();
        // 获取方法上的权限注解
        Permission annotation = method.getAnnotation(Permission.class);
        // 获取权限注解中的角色类型数组
        Role[] values = annotation.value();

        // 将请求包装成Servlet请求属性，获取请求对象
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 获取请求头中的token
        String token = request.getHeader("token");

        // 通过token获取用户的角色
        Integer role = JwtUtil.getRole(token);
        // 根据角色类型获取枚举类型的角色
        Role roleType = Role.getRoleType(role);
        // 遍历权限注解中的角色类型数组
        for (Role type : values) {
            // 如果遍历到的角色类型和当前用户的角色类型一致，则继续执行方法
            if (type.getCode() == roleType.getCode()) {
                return point.proceed();
            }
        }

        // 没有权限，返回权限失败的结果
        return Result.codeFailure(ResultCode.ACCESS_DENIED);
    }

}

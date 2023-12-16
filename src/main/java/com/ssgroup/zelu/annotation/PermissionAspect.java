package com.ssgroup.zelu.annotation;

import com.ssgroup.zelu.annotation.Permission;
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
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Aspect
@Component
@Order(1)
public class PermissionAspect {
    //先用AOP实现，其他有时间再看看能不能直接用原生实现
    //TODO 实现COURSE_ASSISTANT权限的检查

    /**
     * 方法注解切面
     */
    @Pointcut("@annotation(com.ssgroup.zelu.annotation.Permission)")
    private void annotationPointCut() {
    }

    /**
     * 类注解切面
     */
    @Pointcut("@within(com.ssgroup.zelu.annotation.Permission)")
    private void withinPointCut() {
    }


    /**
     * 只有方法有注解执行
     *
     * @param point 函数调用的 ProceedingJoinPoint 对象
     * @return 方法的返回值
     * @throws Throwable 异常信息
     */
    @Around("annotationPointCut() && !withinPointCut()")
    public Object methodCheck(ProceedingJoinPoint point) throws Throwable{

        Role[] value = getMethodValues(point);

        // 没有权限，返回权限失败的结果
        return (checkPermission(value))? point.proceed() : Result.codeFailure(ResultCode.ACCESS_DENIED);
    }

    /**
     * 只有类有注解 执行
     *
     * @param point ProceedingJoinPoint对象，表示当前的方法调用点
     * @return 返回方法的执行结果
     * @throws Throwable 异常信息
     */
    @Around("withinPointCut() && !annotationPointCut()")
    public Object classCheck(ProceedingJoinPoint point) throws Throwable{

        Role[] value = getClassValues(point);

        return (checkPermission(value))? point.proceed() : Result.codeFailure(ResultCode.ACCESS_DENIED);
    }


    /**
     * 类和方法都有注解执行
     *
     * @param point 执行的JoinPoint对象
     * @return 切面方法的返回值
     * @throws Throwable 可抛出的异常
     */
    @Around("withinPointCut() && annotationPointCut()")
    public Object classAndMethodCheck(ProceedingJoinPoint point) throws Throwable {
        //若类 的 isIndividual()为true，则方法中的isIndividual()就不用获取
        boolean flag = false;
        // 获取方法上的Permission注解
        Permission annotation = point.getTarget().getClass().getAnnotation(Permission.class);

        Set<Role> roles = new HashSet<>();

        if (!annotation.isIndividual()) {
            // 如果类上的Permission注解为isIndividual() false，则获取类上的Permission注解中的角色
            Role[] values = annotation.value();
            roles = Arrays.stream(values).collect(Collectors.toSet());
            flag = true;
        }


        // 获取方法对象
        Method method = getMethod(point);
        // 获取方法上的Permission注解
        Permission methodAnnotation = method.getAnnotation(Permission.class);
        if (flag || methodAnnotation.isIndividual()) {
            // 如果方法上的Permission注解为isIndividual() true 或者类的isIndividual() true ，则清空角色集合
            roles.clear();
        }
        // 将方法上的Permission注解中的角色添加到角色集合中
        Collections.addAll(roles, methodAnnotation.value());
        // 将角色集合转换为数组
        Role[] value = roles.toArray(new Role[0]);
        // 判断是否具有执行当前方法的权限，如果没有则返回访问失败的结果
        // 返回访问失败的结果
        return (checkPermission(value))? point.proceed() : Result.codeFailure(ResultCode.ACCESS_DENIED);
    }


    /**
     * 获取Method对象
     *
     * @param point ProceedingJoinPoint
     * @return Method
     */
    private Method getMethod(ProceedingJoinPoint point) {
        // 将point的签名转换为MethodSignature类型
        MethodSignature signature = (MethodSignature) point.getSignature();
        // 返回Method对象
        return signature.getMethod();
    }

    /**
     * 获取方法上的Permission注解的value属性值
     *
     * @param point ProceedingJoinPoint
     * @return Role[]
     *
     */
    private Role[] getMethodValues(ProceedingJoinPoint point) {
        // 获取方法对象
        Method method = getMethod(point);
        // 获取方法上的Permission注解
        Permission annotation = method.getAnnotation(Permission.class);
        Role[] value = annotation.value();
        if (annotation.isAllowAdmin()){
            //若没有定义管理员不能访问，就添加管理员
            value = addRole(value,Role.ADMIN);
        }
        // 返回Permission注解的value属性值
        return value;
    }


    /**
     * 获取指定 ProceedingJoinPoint 对象所属类的 Permission 注解的 value 数组
     *
     * @param point ProceedingJoinPoint 对象，表示当前正在执行的方法调用点
     * @return Permission 注解的 value 数组
     */
    private Role[] getClassValues(ProceedingJoinPoint point) {
        // 获取 point 所属类的 Permission 注解
        Permission annotation = point.getTarget().getClass().getAnnotation(Permission.class);
        Role[] value = annotation.value();
        if (annotation.isAllowAdmin()){
            //若没有定义管理员不能访问，就添加管理员
            value = addRole(value,Role.ADMIN);
        }
        // 返回 Permission 注解的 value 数组
        return value;
    }

    /**
     * 检查权限方法
     *
     * @param roles 权限注解中的角色类型数组
     * @return 如果请求用户的角色与数组中任意一个角色匹配，则返回true；否则返回false
     */
    private boolean checkPermission(Role[] roles) {

        // 将请求包装成Servlet请求属性，获取请求对象
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 获取请求头中的token
        String token = request.getHeader("token");
        //利用parameter进行schoolid的认证
//        String schoolId = request.getParameter("schoolId");
//        System.out.println(schoolId);

        // 通过token获取用户的角色
        Integer role = JwtUtil.getRole(token);

        // 遍历权限注解中的角色类型数组
        for (Role type : roles) {
            // 如果遍历到的角色类型和当前用户的角色类型一致，则继续执行方法
            if (type.getRole() == role) {
                return true;
            }
        }
        return false;
    }


    /**
     * 向角色数组中添加角色
     *
     * @param roles 角色数组
     * @param role 待添加的角色
     *
     * @return 添加角色后的角色数组
     */
    private Role[] addRole(Role[] roles, Role role) {
        // 创建一个新的角色数组，长度为原数组长度加1
        Role[] newRole = new Role[roles.length + 1];
        // 将原角色数组复制到新数组中
        System.arraycopy(roles, 0, newRole, 0, roles.length);
        // 将待添加的角色放入新数组的最后一个位置
        newRole[roles.length - 1] = role;
        // 将新数组赋值给原数组，实现角色的添加
        return newRole;
    }


}

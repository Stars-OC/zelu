package com.ssgroup.zelu.annotation;

import com.ssgroup.zelu.pojo.Result;
import com.ssgroup.zelu.pojo.request.SchoolAndCourseId;
import com.ssgroup.zelu.pojo.type.ResultCode;
import com.ssgroup.zelu.pojo.type.Role;
import com.ssgroup.zelu.pojo.user.User;
import com.ssgroup.zelu.service.CourseService;
import com.ssgroup.zelu.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private CourseService courseService;

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
     * 定义一个私有的类点切口，
     * 用于在具有特定注解但不在特定类中的类上应用。
     */
    @Pointcut("withinPointCut() &&!annotationPointCut()")
    private void classPointCut() {

    }


    /**
     * 用于在具有特定注解但不在特定类中的方法上应用。
     */
    @Pointcut("!withinPointCut() && annotationPointCut()")
    private void methodPointCut() {
    }


    /**
     * 定义一个私有的注解点切口，用于在类和方法上应用。
     */
    @Pointcut("withinPointCut() && annotationPointCut()")
    private void classAndMethodPointCut() {
    }

    /**
     * 指定在方法执行前执行args(request,..)  args(com.ssgroup.zelu.pojo.request.SchoolAndCourseId,..)
     *
     * @param request 学校和课程ID请求
     */
    @Pointcut("args(request,..) args(com.ssgroup.zelu.pojo.request.SchoolAndCourseId,..)")
    private void argsPointCut(SchoolAndCourseId request) {
    }


    /**
     * 在类和方法的切点 around 定义权限检查的切面方法，
     * 对满足条件的类和方法进行权限检查，
     * 如果权限检查通过，则执行被切掉的方法，
     * 否则返回权限失败的结果。
     *
     * @param point 方法连接点
     * @param schoolAndCourseId 学校和课程ID请求
     * @return 方法执行结果或者权限失败的结果
     * @throws Throwable 异常
     */
    @Around("classAndMethodPointCut() && argsPointCut(schoolAndCourseId)")
    public Object classAndMethodArgsCheck(ProceedingJoinPoint point, SchoolAndCourseId schoolAndCourseId) throws Throwable {
        Role[] value = getClassAndMethodValue(point);
        // 没有权限，返回权限失败的结果
        return (checkPermission(value, schoolAndCourseId)) ? point.proceed() : Result.codeFailure(ResultCode.ACCESS_DENIED);
    }


    /**
     * 对方法执行前进行权限检查，
     * 如果方法权限为空或者方法参数schoolId的值不在方法权限范围内，则返回权限失败的结果。
     *
     * @param point 方法连接点
     * @param schoolAndCourseId 学校和课程ID请求
     * @return 方法执行结果或者权限失败的结果
     * @throws Throwable 异常
     */
    @Around("methodPointCut() && argsPointCut(schoolAndCourseId)")
    public Object methodArgsCheck(ProceedingJoinPoint point, SchoolAndCourseId schoolAndCourseId) throws Throwable {
        Role[] value = getMethodValues(point);
        // 没有权限，返回权限失败的结果
        return (checkPermission(value, schoolAndCourseId)) ? point.proceed() : Result.codeFailure(ResultCode.ACCESS_DENIED);
    }


    /**
     * 对方法执行前进行权限检查，
     * 如果方法所属的类权限为空或者方法参数schoolId的值不在方法权限范围内，则返回权限失败的结果。
     *
     * @param point 方法连接点
     * @param schoolAndCourseId 学校和课程ID请求
     * @return 方法执行结果或者权限失败的结果
     * @throws Throwable 异常
     */
    @Around("classPointCut() && argsPointCut(schoolAndCourseId)")
    public Object classArgsCheck(ProceedingJoinPoint point, SchoolAndCourseId schoolAndCourseId) throws Throwable {
        Role[] value = getClassValues(point);
        // 没有权限，返回权限失败的结果
        return (checkPermission(value, schoolAndCourseId)) ? point.proceed() : Result.codeFailure(ResultCode.ACCESS_DENIED);
    }


    /**
     * 只有方法有注解执行
     *
     * @param point 函数调用的 ProceedingJoinPoint 对象
     * @return 方法的返回值
     * @throws Throwable 异常信息
     */
    @Around("methodPointCut()")
    public Object methodCheck(ProceedingJoinPoint point) throws Throwable{


        Role[] value = getMethodValues(point);
        String token = getToken();

        // 没有权限，返回权限失败的结果
        return (checkPermission(value,token))? point.proceed() : Result.codeFailure(ResultCode.ACCESS_DENIED);
    }

    /**
     * 只有类有注解 执行
     *
     * @param point ProceedingJoinPoint对象，表示当前的方法调用点
     * @return 返回方法的执行结果
     * @throws Throwable 异常信息
     */
    @Around("classPointCut())")
    public Object classCheck(ProceedingJoinPoint point) throws Throwable{

        Role[] value = getClassValues(point);
        String token = getToken();

        return (checkPermission(value,token))? point.proceed() : Result.codeFailure(ResultCode.ACCESS_DENIED);
    }


    /**
     * 类和方法都有注解执行
     *
     * @param point 执行的JoinPoint对象
     * @return 切面方法的返回值
     * @throws Throwable 可抛出的异常
     */
    @Around("classAndMethodPointCut()")
    public Object classAndMethodCheck(ProceedingJoinPoint point) throws Throwable {
        // 获取类和方法的注解值
        Role[] roles = getClassAndMethodValue(point);
        // 获取令牌
        String token = getToken();
        // 检查权限
        return (checkPermission(roles,token))? point.proceed() : Result.codeFailure(ResultCode.ACCESS_DENIED);
    }


    /**
     * 获取类和方法的权限值
     *
     * @param point 方法连接点
     * @return 权限值数组
     * @throws Throwable 异常
     */
    private Role[] getClassAndMethodValue(ProceedingJoinPoint point) throws Throwable {
        //若类 的 isIndividual()为true，则方法中的isIndividual()就不用获取
        boolean flag = false;
        // 获取类上的Permission注解
        Permission classAnnotation = point.getTarget().getClass().getAnnotation(Permission.class);

        Set<Role> roles = new HashSet<>();

        if (!classAnnotation.isIndividual()) {
            // 如果类上的Permission注解为isIndividual() false，则获取类上的Permission注解中的角色
            Role[] values = classAnnotation.value();
            roles = Arrays.stream(values).collect(Collectors.toSet());
        }else {
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

        // 如果都没有限制admin，admin就有权限
        if (classAnnotation.isAllowAdmin() && methodAnnotation.isAllowAdmin()){
            roles.add(Role.ADMIN);
        }
        // 将方法上的Permission注解中的角色添加到角色集合中
        Collections.addAll(roles, methodAnnotation.value());
        // 将角色集合转换为数组
        Role[] value = roles.toArray(new Role[0]);
        // 返回角色数组
        return value;
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
     * @param token 令牌
     * @return 如果请求用户的角色与数组中任意一个角色匹配，则返回true；否则返回false
     */
    private boolean checkPermission(Role[] roles,String token) {

        // 通过token获取用户的角色
        Integer role = JwtUtil.getRole(token);

        // 遍历权限注解中的角色类型数组
        return Role.checkRole(roles,role);
    }

    /**
     * 校验角色是否有权限
     *
     * @param roles 角色数组
     * @param schoolAndCourseId 学校和课程ID请求
     * @return 如果有权限返回true，否则返回false
     */
    private boolean checkPermission(Role[] roles, SchoolAndCourseId schoolAndCourseId) {

        // 获取Token
        String token = getToken();

        // 创建User对象并获取部门ID、角色和用户名
        User user = new User(token);
        long deptId = user.getDeptId();
        int role = user.getRole();
        long username = user.getUsername();

        // 如果课程ID为0且部门ID与学校ID相等，则为学校的校验权限
        if (schoolAndCourseId.getCourseId() == 0 && deptId == schoolAndCourseId.getSchoolId()){
            return Role.checkRole(roles,role);
        }

        // 如果角色是课程角色，则为课程的校验权限
        if (Role.isCourseRole(roles)){
            return courseService.checkRole(role,username,schoolAndCourseId) && Role.checkRole(roles,role);
        }

        // 其他情况无权限
        return false;
    }

    /**
     * 从header中获取token
     *
     * @return token
     */
    private String getToken(){
        // 将请求包装成Servlet请求属性，获取请求对象
        HttpServletRequest request = getRequest();
        // 获取请求头中的Token
        String token = request.getHeader("token");
        return token;
    }


    /**
     * 获取HttpServletRequest对象
     *
     * @return HttpServletRequest对象
     */
    private HttpServletRequest getRequest(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        return request;
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

        int length = roles.length;
        // 创建一个新的角色数组，长度为原数组长度加1
        Role[] newRole = new Role[length + 1];
        // 将原角色数组复制到新数组中
        System.arraycopy(roles, 0, newRole, 0, length);
        // 将待添加的角色放入新数组的最后一个位置
        newRole[length] = role;
        // 将新数组赋值给原数组，实现角色的添加
        return newRole;
    }


}

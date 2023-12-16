package com.ssgroup.zelu.annotation;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ssgroup.zelu.mapper.UserMapper;
import com.ssgroup.zelu.pojo.user.User;
import com.ssgroup.zelu.service.DeptService;
import com.ssgroup.zelu.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class RequestTokenResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private DeptService deptService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(RequestToken.class);
    }

    /**
     * 添加@RestController中的参数解析方法。
     * 解析header中的token，并解析出各种对象对象。
     *
     * @return 解析后的值 User/School/Company
     * @throws Exception 如果解析过程中发生异常
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        // 获取注解
        RequestToken annotation = parameter.getParameterAnnotation(RequestToken.class);

        // 获取HTTP请求
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);

        // 获取请求头
        String token = request.getHeader("token");

        String value = annotation.value().toLowerCase();

        //判断注解是用户
        if ("user".equalsIgnoreCase(value)){
            return new User(token);
        }

        long deptType = JwtUtil.getDeptId(token);

        return deptService.checkDept(deptType);

    }

}
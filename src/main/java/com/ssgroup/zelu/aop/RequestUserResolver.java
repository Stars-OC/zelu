package com.ssgroup.zelu.aop;

import com.ssgroup.zelu.pojo.user.User;
import com.ssgroup.zelu.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.reflect.Type;

public class RequestUserResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(RequestUser.class);
    }

    /**
     * 添加@RestController中的参数解析方法。
     * 解析header中的token，并解析出User对象。
     *
     * @return 解析后的值 User
     * @throws Exception 如果解析过程中发生异常
     */
    @Override
    public User resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        // 获取注解
        RequestUser annotation = parameter.getParameterAnnotation(RequestUser.class);

        // 获取HTTP请求
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);

        // 获取请求头
        String header = request.getHeader(annotation.value());

        // 创建并返回User对象
        return new User(header);
    }

}
package com.ssgroup.zelu.annotation;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.ssgroup.zelu.annotation.RequestPage;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class RequestPageResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(RequestPage.class);
    }

    /**
     * 重写的方法，用于解析方法参数。
     *
     * @param parameter 方法参数
     * @param mavContainer ModelAndViewContainer对象
     * @param webRequest NativeWebRequest对象
     * @param binderFactory WebDataBinderFactory对象
     * @return 解析得到的参数数组
     * @throws Exception 解析过程中出现异常时抛出
     */
    @Override
    public int[] resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        // 获取方法参数上的RequestPage注解
        RequestPage annotation = parameter.getParameterAnnotation(RequestPage.class);

        // 创建一个包含两个元素的整型数组
        int[] args = {annotation.page(),annotation.limit()};

        // 从请求中获取page参数，并根据注解的page()值判断是否需要修改args[0]的值
        String page = webRequest.getParameter("page");
        args[0] = judge(page,annotation.page());

        // 从请求中获取limit参数，并根据注解的limit()值判断是否需要修改args[1]的值
        String limit = webRequest.getParameter("limit");
        args[1] = judge(limit,annotation.limit());

        // 返回修改后的整型数组
        return args;
    }


    private int judge(String param,int annotation){
        if (StringUtils.isEmpty(param)) return annotation;
        return  Integer.parseInt(param);
    }
}

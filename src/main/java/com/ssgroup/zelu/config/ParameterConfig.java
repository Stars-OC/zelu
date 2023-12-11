package com.ssgroup.zelu.config;

import com.ssgroup.zelu.aop.RequestUserResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class ParameterConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers){
        argumentResolvers.add(new RequestUserResolver());
    }
}
